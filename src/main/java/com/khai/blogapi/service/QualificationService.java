package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.model.Gender;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.GenderRequest;
import com.khai.blogapi.payload.GenderResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface GenderService {

	PageResponse<GenderResponse> getAllGenders(Integer page, Integer size);

	List<GenderResponse> getGenders();

	GenderResponse getGenderById(Long GenderId);

	Gender getGender(Long GenderId);

	GenderResponse createGender(GenderRequest GenderRequest, UserPrincipal userPrincipal);

	ApiResponse deleteGenderById(Long GenderId, UserPrincipal userPrincipal);

	ApiResponse deleteAll();

	GenderResponse updateGenderById(Long GenderId, GenderRequest GenderRequest, UserPrincipal userPrincipal);
	

}
