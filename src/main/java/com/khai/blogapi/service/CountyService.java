package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.CountyRequest;
import com.khai.blogapi.payload.CountyResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface CountyService {

	PageResponse<CountyResponse> getAllCounties(Integer page, Integer size);

	List<CountyResponse> getCounties();

	CountyResponse getCountyById(Long CountyId);

	CountyResponse createCounty(CountyRequest CountyRequest, UserPrincipal userPrincipal);

	ApiResponse deleteCountyById(Long CountyId, UserPrincipal userPrincipal);

	ApiResponse deleteAll();

	CountyResponse updateCountyById(Long CountyId, CountyRequest CountyRequest, UserPrincipal userPrincipal);
	

}
