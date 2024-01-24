package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.model.Department;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.DepartmentRequest;
import com.khai.blogapi.payload.DepartmentResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface DepartmentService {

	PageResponse<DepartmentResponse> getAllDepartments(Integer page, Integer size);

	List<DepartmentResponse> getDepartments();

	DepartmentResponse getDepartmentById(Long DepartmentId);

	Department getDepartment(Long DepartmentId);

	DepartmentResponse createDepartment(DepartmentRequest DepartmentRequest, UserPrincipal userPrincipal);

	ApiResponse deleteDepartmentById(Long DepartmentId, UserPrincipal userPrincipal);

	ApiResponse deleteAll();

	DepartmentResponse updateDepartmentById(Long DepartmentId, DepartmentRequest DepartmentRequest, UserPrincipal userPrincipal);
}
