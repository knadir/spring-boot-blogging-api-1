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
import com.khai.blogapi.payload.CountyRequest;
import com.khai.blogapi.payload.CountyResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.CountyService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/counties")
public class CountyController {

	@Autowired
	CountyService countyService;

	@Autowired
	BlogService blogService;

	@GetMapping
	public ResponseEntity<PageResponse<CountyResponse>> getAllCounties(
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<CountyResponse> countyResponse = countyService.getAllCounties(page, size);
		return new ResponseEntity<>(countyResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<CountyResponse>> getCounties() {
		List<CountyResponse> countyResponse = countyService.getCounties();
		return new ResponseEntity<>(countyResponse, HttpStatus.OK);
	}

	@GetMapping("/getEntity/{entity_id}")
	public ResponseEntity<CountyResponse> getCountyByEntityId(
			@PathVariable("entity_id") Long countyId) {
		CountyResponse countyResponse = countyService.getCountyById(countyId);
		return new ResponseEntity<>(countyResponse, HttpStatus.OK);
	}

	@GetMapping("/{county_id}")
	public ResponseEntity<CountyResponse> getCountyById(
			@PathVariable("county_id") Long countyId) {
		CountyResponse countyResponse = countyService.getCountyById(countyId);
		return new ResponseEntity<>(countyResponse, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<CountyResponse> createCounty(
			@RequestBody final CountyRequest countyRequest,
			@CurrentUser UserPrincipal userPrincipal) {

		//System.out.println("countyRequest..." + countyRequest);
		//System.out.println("userPrincipal..." + userPrincipal);

		CountyResponse countyResponse = countyService.createCounty(countyRequest, userPrincipal);
		return new ResponseEntity<>(countyResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{county_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteCountyById(
			@PathVariable("county_id") Long countyId,
			@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = countyService.deleteCountyById(countyId, userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{county_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<CountyResponse> updateCountyById(
			@PathVariable("county_id") Long countyId,
			@RequestBody CountyRequest countyRequest,
			@CurrentUser UserPrincipal userPrincipal) {
		CountyResponse countyResponse = countyService.updateCountyById(countyId, countyRequest, userPrincipal);
		return new ResponseEntity<>(countyResponse, HttpStatus.OK);
	}

}
