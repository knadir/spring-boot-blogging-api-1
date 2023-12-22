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
import com.khai.blogapi.payload.EmployerRequest;
import com.khai.blogapi.payload.EmployerResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.EmployerService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/employers")
public class EmployerController {

	@Autowired
	EmployerService employerService;

	@Autowired
	BlogService blogService;

	@GetMapping
	public ResponseEntity<PageResponse<EmployerResponse>> getAllEmployers(
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<EmployerResponse> employerResponse = employerService.getAllEmployers(page, size);
		return new ResponseEntity<>(employerResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EmployerResponse>> getEmployers() {
		List<EmployerResponse> employerResponse = employerService.getEmployers();
		return new ResponseEntity<>(employerResponse, HttpStatus.OK);
	}

	@GetMapping("/{employer_id}")
	public ResponseEntity<EmployerResponse> getEmployer(
			@PathVariable("employer_id") Long employerId) {
		EmployerResponse employerResponse = employerService.getEmployerById(employerId);
		return new ResponseEntity<>(employerResponse, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<EmployerResponse> createEmployer(
			@RequestBody final EmployerRequest employerRequest,
			@CurrentUser UserPrincipal userPrincipal) {
		EmployerResponse employerResponse = employerService.createEmployer(employerRequest, userPrincipal);
		return new ResponseEntity<>(employerResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{employer_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteEmployerById(
			@PathVariable("employer_id") Long employerId,
			@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = employerService.deleteEmployerById(employerId, userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{employer_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<EmployerResponse> updateEmployerById(
			@PathVariable("employer_id") Long employerId,
			@RequestBody EmployerRequest employerRequest,
			@CurrentUser UserPrincipal userPrincipal) {
            //System.out.println("employerRequest Controller...."+employerRequest);
		EmployerResponse employerResponse = employerService.updateEmployerById(employerId, employerRequest,
				userPrincipal);
	     //System.out.println("employerResponse Controller...."+employerResponse);
		return new ResponseEntity<>(employerResponse, HttpStatus.OK);
	}

}
