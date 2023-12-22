package com.khai.blogapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.QualificationRequest;
import com.khai.blogapi.payload.QualificationResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.QualificationService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/qualifications")
public class QualificationController {

	@Autowired
	QualificationService qualificationService;

	@Autowired
	BlogService blogService;

	@GetMapping
	public ResponseEntity<PageResponse<QualificationResponse>> getAllQualifications(
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<QualificationResponse> qualificationResponse = qualificationService.getAllQualifications(page, size);
		return new ResponseEntity<>(qualificationResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<QualificationResponse>> getQualifications() {
		List<QualificationResponse> qualificationResponse = qualificationService.getQualifications();
		return new ResponseEntity<>(qualificationResponse, HttpStatus.OK);
	}

	@GetMapping("/{qualification_id}")
	public ResponseEntity<QualificationResponse> getQualificationById(
			@PathVariable("qualification_id") Long qualificationId) {
		QualificationResponse qualificationResponse = qualificationService.getQualificationById(qualificationId);
		return new ResponseEntity<>(qualificationResponse, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<QualificationResponse> createQualification(
			@RequestBody final QualificationRequest qualificationRequest,
			@CurrentUser UserPrincipal userPrincipal) {

		//System.out.println("qualificationRequest..." + qualificationRequest);
		//System.out.println("userPrincipal..." + userPrincipal);

		QualificationResponse qualificationResponse = qualificationService.createQualification(qualificationRequest, userPrincipal);
		return new ResponseEntity<>(qualificationResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{qualification_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteQualificationById(
			@PathVariable("qualification_id") Long qualificationId,
			@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = qualificationService.deleteQualificationById(qualificationId, userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{qualification_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<QualificationResponse> updateQualificationById(
			@PathVariable("qualification_id") Long qualificationId,
			@RequestBody QualificationRequest qualificationRequest,
			@CurrentUser UserPrincipal userPrincipal) {
		QualificationResponse qualificationResponse = qualificationService.updateQualificationById(qualificationId, qualificationRequest, userPrincipal);
		return new ResponseEntity<>(qualificationResponse, HttpStatus.OK);
	}

}
