package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.model.Municipality;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.MunicipalityRequest;
import com.khai.blogapi.payload.MunicipalityResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface MunicipalityService {

	PageResponse<MunicipalityResponse> getAllMunicipalities(Integer page, Integer size);

	List<MunicipalityResponse> getMunicipalities();

	MunicipalityResponse getMunicipalityById(Long MunicipalityId);

	Municipality getMunicipality(Long MunicipalityId);

	MunicipalityResponse createMunicipality(MunicipalityRequest MunicipalityRequest, UserPrincipal userPrincipal);

	ApiResponse deleteMunicipalityById(Long MunicipalityId, UserPrincipal userPrincipal);

	ApiResponse deleteAll();

	MunicipalityResponse updateMunicipalityById(Long MunicipalityId, MunicipalityRequest MunicipalityRequest, UserPrincipal userPrincipal);
	

}
