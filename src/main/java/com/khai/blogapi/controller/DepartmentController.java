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
import com.khai.blogapi.payload.DepartmentRequest;
import com.khai.blogapi.payload.DepartmentResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.DepartmentService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/departments")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@Autowired
	BlogService blogService;

	@GetMapping
	public ResponseEntity<PageResponse<DepartmentResponse>> getAllDepartments(
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<DepartmentResponse> departmentResponse = departmentService.getAllDepartments(page, size);
		return new ResponseEntity<>(departmentResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<DepartmentResponse>> getDepartments() {
		List<DepartmentResponse> departmentResponse = departmentService.getDepartments();
		return new ResponseEntity<>(departmentResponse, HttpStatus.OK);
	}

	@GetMapping("/{department_id}")
	public ResponseEntity<DepartmentResponse> getDepartmentById(
			@PathVariable("department_id") Long departmentId) {
		DepartmentResponse departmentResponse = departmentService.getDepartmentById(departmentId);
		return new ResponseEntity<>(departmentResponse, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<DepartmentResponse> createDepartment(
			@RequestBody final DepartmentRequest departmentRequest,
			@CurrentUser UserPrincipal userPrincipal) {

		//System.out.println("departmentRequest..." + departmentRequest);
		//System.out.println("userPrincipal..." + userPrincipal);

		DepartmentResponse departmentResponse = departmentService.createDepartment(departmentRequest, userPrincipal);
		return new ResponseEntity<>(departmentResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{department_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteDepartmentById(
			@PathVariable("department_id") Long departmentId,
			@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = departmentService.deleteDepartmentById(departmentId, userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{department_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<DepartmentResponse> updateDepartmentById(
			@PathVariable("department_id") Long departmentId,
			@RequestBody DepartmentRequest departmentRequest,
			@CurrentUser UserPrincipal userPrincipal) {
		DepartmentResponse departmentResponse = departmentService.updateDepartmentById(departmentId, departmentRequest, userPrincipal);
		return new ResponseEntity<>(departmentResponse, HttpStatus.OK);
	}

}
