package com.khai.blogapi.serviceImpl;

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

import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Employer;
import com.khai.blogapi.model.Municipality;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.EmployerRequest;
import com.khai.blogapi.payload.EmployerResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.EmployerRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.EmployerService;
import com.khai.blogapi.service.MunicipalityService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class EmployerServiceImpl implements EmployerService {

	@Autowired
	EmployerRepository employerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	MunicipalityService municipalityService;

	@Override
	public PageResponse<EmployerResponse> getAllEmployers(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<Employer> employers = employerRepository.findAll(pageable);
		List<EmployerResponse> employerResponses = Arrays
				.asList(modelMapper.map(employers.getContent(), EmployerResponse[].class));

		PageResponse<EmployerResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(employerResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(employers.getNumberOfElements());
		pageResponse.setTotalPages(employers.getTotalPages());
		pageResponse.setLast(employers.isLast());

		return pageResponse;
	}

	@Override
	public List<EmployerResponse> getEmployers() {
		List<Employer> employers = StreamSupport
				.stream(employerRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return mapper.employersToEmployerResponse(employers);
	}

	@Override
	public EmployerResponse getEmployerById(Long employerId) {
		Employer employer = employerRepository.findById(employerId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.EMPLOYER_NOT_FOUND + employerId));

		return modelMapper.map(employer, EmployerResponse.class);
	}

	@Override
	public Employer getEmployer(Long employerId) {
		return employerRepository.findById(employerId)
				.orElseThrow(() -> new IllegalArgumentException("could not find employers with id: " + employerId));
	}

	@Override
	public EmployerResponse createEmployer(EmployerRequest employerRequest, UserPrincipal userPrincipal) {
		System.out.println("employerRequest..." + employerRequest);
		System.out.println("userPrincipal..." + userPrincipal);

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Employer employer = modelMapper.map(employerRequest, Employer.class); 

		System.out.println("employer..." + employer);

		// if (employerRepository.findByName(employer.getName()).isPresent()) {
		// throw new ResourceExistException(AppConstant.EMPLOYER_EXIST);
		// }

		Municipality municipalityBorn = municipalityService.getMunicipality(employerRequest.getMunicipalityBornId());
		employer.setMunicipalityBorn(municipalityBorn);

		Municipality municipalityAddr = municipalityService.getMunicipality(employerRequest.getMunicipalityAddrId());
		employer.setMunicipalityAddr(municipalityAddr);

		employerRepository.save(employer);

		return modelMapper.map(employer, EmployerResponse.class);

	}

	@Override
	public ApiResponse deleteEmployerById(Long employerId, UserPrincipal userPrincipal) {
		Employer employer = employerRepository.findById(employerId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.EMPLOYER_NOT_FOUND + employerId));

		employerRepository.delete(employer);
		return new ApiResponse(Boolean.TRUE, AppConstant.EMPLOYER_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteAll() {
		employerRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE, AppConstant.EMPLOYER_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public EmployerResponse updateEmployerById(Long employerId, EmployerRequest employerRequest,
			UserPrincipal userPrincipal) {

		// if ((employerRepository.existsByName(employerRequest.getName()))
		// &&
		// (employerRepository.existsByMunicipalityId(employerRequest.getMunicipalityId())))
		// {
		// throw new ResourceExistException(AppConstant.EMPLOYER_EXIST);
		// }

		modelMapper.typeMap(EmployerRequest.class, Employer.class).addMappings(mapper -> mapper.skip(Employer::setId));

		Employer employer = employerRepository.findById(employerId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.EMPLOYER_NOT_FOUND + employerId));

		modelMapper.map(employerRequest, employer);

		employerRepository.save(employer);

		return modelMapper.map(employer, EmployerResponse.class);

	}
}
