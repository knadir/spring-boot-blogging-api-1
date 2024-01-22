package com.khai.blogapi.service;

import com.khai.blogapi.model.Bank;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.BankRequest;
import com.khai.blogapi.payload.BankResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;
import java.util.List;

public interface BankService {
  PageResponse<BankResponse> getAllBanks(Integer page, Integer size);

  List<BankResponse> getBanks();

  BankResponse getBankById(Long BankId);

  Bank getBank(Long BankId);

  BankResponse createBank(BankRequest BankRequest, UserPrincipal userPrincipal);

  ApiResponse deleteBankById(Long BankId, UserPrincipal userPrincipal);

  ApiResponse deleteAll();

  BankResponse updateBankById(
    Long BankId,
    BankRequest BankRequest,
    UserPrincipal userPrincipal
  );
}
