package com.khai.blogapi.serviceImpl;

import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Bank;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.BankRequest;
import com.khai.blogapi.payload.BankResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.BankRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BankService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

  @Autowired
  BankRepository bankRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ModelMapper modelMapper;

  @Override
  public PageResponse<BankResponse> getAllBanks(Integer page, Integer size) {
    AppUtils.validatePageAndSize(page, size);
    Pageable pageable = PageRequest.of(page, size);

    Page<Bank> banks = bankRepository.findAll(pageable);
    List<BankResponse> bankResponses = Arrays.asList(
      modelMapper.map(banks.getContent(), BankResponse[].class)
    );

    PageResponse<BankResponse> pageResponse = new PageResponse<>();
    pageResponse.setContent(bankResponses);
    pageResponse.setPage(page);
    pageResponse.setSize(size);
    pageResponse.setTotalElements(banks.getNumberOfElements());
    pageResponse.setTotalPages(banks.getTotalPages());
    pageResponse.setLast(banks.isLast());

    return pageResponse;
  }

  @Override
  public List<BankResponse> getBanks() {
    List<Bank> banks = StreamSupport
      .stream(bankRepository.findAll().spliterator(), false)
      .collect(Collectors.toList());
    return mapper.banksToBankResponse(banks);
  }

  @Override
  public BankResponse getBankById(Long bankId) {
    Bank bank = bankRepository
      .findById(bankId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          AppConstant.QUALIFICATION_NOT_FOUND + bankId
        )
      );
    // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    // return modelMapper.map(bank, BankResponse.class);
    return mapper.bankToBankResponse(bank);
  }

  @Override
  public Bank getBank(Long bankId) {
    return bankRepository
      .findById(bankId)
      .orElseThrow(() ->
        new IllegalArgumentException("could not find banks with id: " + bankId)
      );
  }

  @Override
  public BankResponse createBank(
    BankRequest bankRequest,
    UserPrincipal userPrincipal
  ) {
    Bank bank = modelMapper.map(bankRequest, Bank.class);

    if (bankRepository.findByName(bank.getName()).isPresent()) {
      throw new ResourceExistException(AppConstant.QUALIFICATION_EXIST);
    }

    bankRepository.save(bank);

    return modelMapper.map(bank, BankResponse.class);
  }

  @Override
  public ApiResponse deleteBankById(Long bankId, UserPrincipal userPrincipal) {
    Bank bank = bankRepository
      .findById(bankId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          AppConstant.QUALIFICATION_NOT_FOUND + bankId
        )
      );

    bankRepository.delete(bank);
    return new ApiResponse(
      Boolean.TRUE,
      AppConstant.QUALIFICATION_DELETE_MESSAGE,
      HttpStatus.OK
    );
  }

  @Override
  public ApiResponse deleteAll() {
    bankRepository.deleteAll();
    return new ApiResponse(
      Boolean.TRUE,
      AppConstant.QUALIFICATION_DELETE_MESSAGE,
      HttpStatus.OK
    );
  }

  @Override
  public BankResponse updateBankById(
    Long bankId,
    BankRequest bankRequest,
    UserPrincipal userPrincipal
  ) {
    // if (bankRepository.existsByName(bankRequest.getName())) {
    // throw new ResourceExistException(AppConstant.QUALIFICATION_EXIST);
    // }

    modelMapper
      .typeMap(BankRequest.class, Bank.class)
      .addMappings(mapper -> mapper.skip(Bank::setId));

    Bank bank = bankRepository
      .findById(bankId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          AppConstant.QUALIFICATION_NOT_FOUND + bankId
        )
      );

    modelMapper.map(bankRequest, bank);

    bankRepository.save(bank);

    return modelMapper.map(bank, BankResponse.class);
  }
}
