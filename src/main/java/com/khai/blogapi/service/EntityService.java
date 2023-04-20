package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.EntityRequest;
import com.khai.blogapi.payload.EntityResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface EntityService {

	PageResponse<EntityResponse> getAllEntities(Integer page, Integer size);

	List<EntityResponse> getEntities();

	EntityResponse getEntityById(Long EntityId);

	EntityResponse createEntity(EntityRequest EntityRequest, UserPrincipal userPrincipal);

	ApiResponse deleteEntityById(Long EntityId, UserPrincipal userPrincipal);

	ApiResponse deleteAll();

	EntityResponse updateEntityById(Long EntityId, EntityRequest EntityRequest, UserPrincipal userPrincipal);
	

}
