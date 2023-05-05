package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.model.Employer;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.EmployerRequest;
import com.khai.blogapi.payload.EmployerResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface EmployerService {

	PageResponse<EmployerResponse> getAllEmployers(Integer page, Integer size);

	List<EmployerResponse> getEmployers();

	EmployerResponse getEmployerById(Long EmployerId);

	Employer getEmployer(Long EmployerId);

	EmployerResponse createEmployer(EmployerRequest EmployerRequest, UserPrincipal userPrincipal);

	ApiResponse deleteEmployerById(Long EmployerId, UserPrincipal userPrincipal);

	ApiResponse deleteAll();

	EmployerResponse updateEmployerById(Long EmployerId, EmployerRequest EmployerRequest, UserPrincipal userPrincipal);
	

}
