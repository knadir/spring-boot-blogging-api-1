package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.model.Qualification;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.QualificationRequest;
import com.khai.blogapi.payload.QualificationResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface QualificationService {

	PageResponse<QualificationResponse> getAllQualifications(Integer page, Integer size);

	List<QualificationResponse> getQualifications();

	QualificationResponse getQualificationById(Long QualificationId);

	Qualification getQualification(Long QualificationId);

	QualificationResponse createQualification(QualificationRequest QualificationRequest, UserPrincipal userPrincipal);

	ApiResponse deleteQualificationById(Long QualificationId, UserPrincipal userPrincipal);

	ApiResponse deleteAll();

	QualificationResponse updateQualificationById(Long QualificationId, QualificationRequest QualificationRequest, UserPrincipal userPrincipal);
}
