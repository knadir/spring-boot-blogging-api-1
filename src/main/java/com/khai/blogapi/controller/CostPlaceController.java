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
import com.khai.blogapi.payload.CostPlaceRequest;
import com.khai.blogapi.payload.CostPlaceResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.CostPlaceService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/costPlaces")
public class CostPlaceController {

	@Autowired
	CostPlaceService costPlaceService;

	@Autowired
	BlogService blogService;

	@GetMapping
	public ResponseEntity<PageResponse<CostPlaceResponse>> getAllCostPlaces(
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<CostPlaceResponse> costPlaceResponse = costPlaceService.getAllCostPlaces(page, size);
		return new ResponseEntity<>(costPlaceResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<CostPlaceResponse>> getCostPlaces() {
		List<CostPlaceResponse> costPlaceResponse = costPlaceService.getCostPlaces();
		return new ResponseEntity<>(costPlaceResponse, HttpStatus.OK);
	}

	@GetMapping("/{costPlace_id}")
	public ResponseEntity<CostPlaceResponse> getCostPlaceById(
			@PathVariable("costPlace_id") Long costPlaceId) {
		CostPlaceResponse costPlaceResponse = costPlaceService.getCostPlaceById(costPlaceId);
		return new ResponseEntity<>(costPlaceResponse, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<CostPlaceResponse> createCostPlace(
			@RequestBody final CostPlaceRequest costPlaceRequest,
			@CurrentUser UserPrincipal userPrincipal) {

		//System.out.println("costPlaceRequest..." + costPlaceRequest);
		//System.out.println("userPrincipal..." + userPrincipal);

		CostPlaceResponse costPlaceResponse = costPlaceService.createCostPlace(costPlaceRequest, userPrincipal);
		return new ResponseEntity<>(costPlaceResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{costPlace_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteCostPlaceById(
			@PathVariable("costPlace_id") Long costPlaceId,
			@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = costPlaceService.deleteCostPlaceById(costPlaceId, userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{costPlace_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<CostPlaceResponse> updateCostPlaceById(
			@PathVariable("costPlace_id") Long costPlaceId,
			@RequestBody CostPlaceRequest costPlaceRequest,
			@CurrentUser UserPrincipal userPrincipal) {
		CostPlaceResponse costPlaceResponse = costPlaceService.updateCostPlaceById(costPlaceId, costPlaceRequest, userPrincipal);
		return new ResponseEntity<>(costPlaceResponse, HttpStatus.OK);
	}

}
