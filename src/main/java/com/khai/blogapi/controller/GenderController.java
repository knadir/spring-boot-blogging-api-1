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
import com.khai.blogapi.payload.GenderRequest;
import com.khai.blogapi.payload.GenderResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.GenderService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/genders")
public class GenderController {

	@Autowired
	GenderService genderService;

	@Autowired
	BlogService blogService;

	@GetMapping
	public ResponseEntity<PageResponse<GenderResponse>> getAllGenders(
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<GenderResponse> genderResponse = genderService.getAllGenders(page, size);
		return new ResponseEntity<>(genderResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<GenderResponse>> getGenders() {
		List<GenderResponse> genderResponse = genderService.getGenders();
		return new ResponseEntity<>(genderResponse, HttpStatus.OK);
	}

	@GetMapping("/{gender_id}")
	public ResponseEntity<GenderResponse> getGenderById(
			@PathVariable("gender_id") Long genderId) {
		GenderResponse genderResponse = genderService.getGenderById(genderId);
		return new ResponseEntity<>(genderResponse, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<GenderResponse> createGender(
			@RequestBody final GenderRequest genderRequest,
			@CurrentUser UserPrincipal userPrincipal) {

		System.out.println("genderRequest..." + genderRequest);
		System.out.println("userPrincipal..." + userPrincipal);

		GenderResponse genderResponse = genderService.createGender(genderRequest, userPrincipal);
		return new ResponseEntity<>(genderResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{gender_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteGenderById(
			@PathVariable("gender_id") Long genderId,
			@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = genderService.deleteGenderById(genderId, userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{gender_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<GenderResponse> updateGenderById(
			@PathVariable("gender_id") Long genderId,
			@RequestBody GenderRequest genderRequest,
			@CurrentUser UserPrincipal userPrincipal) {
		GenderResponse genderResponse = genderService.updateGenderById(genderId, genderRequest, userPrincipal);
		return new ResponseEntity<>(genderResponse, HttpStatus.OK);
	}

}
