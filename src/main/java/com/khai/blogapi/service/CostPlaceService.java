package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.model.CostPlace;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.CostPlaceRequest;
import com.khai.blogapi.payload.CostPlaceResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface CostPlaceService {

	PageResponse<CostPlaceResponse> getAllCostPlaces(Integer page, Integer size);

	List<CostPlaceResponse> getCostPlaces();

	CostPlaceResponse getCostPlaceById(Long CostPlaceId);

	CostPlace getCostPlace(Long CostPlaceId);

	CostPlaceResponse createCostPlace(CostPlaceRequest CostPlaceRequest, UserPrincipal userPrincipal);

	ApiResponse deleteCostPlaceById(Long CostPlaceId, UserPrincipal userPrincipal);

	ApiResponse deleteAll();

	CostPlaceResponse updateCostPlaceById(Long CostPlaceId, CostPlaceRequest CostPlaceRequest, UserPrincipal userPrincipal);
}
