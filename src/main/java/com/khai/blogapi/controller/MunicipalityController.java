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
import com.khai.blogapi.payload.MunicipalityRequest;
import com.khai.blogapi.payload.MunicipalityResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.MunicipalityService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/municipalities")
public class MunicipalityController {

	@Autowired
	MunicipalityService municipalityService;

	@Autowired
	BlogService blogService;

	@GetMapping
	public ResponseEntity<PageResponse<MunicipalityResponse>> getAllMunicipalities(
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<MunicipalityResponse> municipalityResponse = municipalityService.getAllMunicipalities(page, size);
		return new ResponseEntity<>(municipalityResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<MunicipalityResponse>> getMunicipalities() {
		List<MunicipalityResponse> municipalityResponse = municipalityService.getMunicipalities();
		return new ResponseEntity<>(municipalityResponse, HttpStatus.OK);
	}

	@GetMapping("/{municipality_id}")
	public ResponseEntity<MunicipalityResponse> getMunicipality(
			@PathVariable("municipality_id") Long municipalityId) {
		MunicipalityResponse municipalityResponse = municipalityService.getMunicipalityById(municipalityId);
		return new ResponseEntity<>(municipalityResponse, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<MunicipalityResponse> createMunicipality(
			@RequestBody final MunicipalityRequest municipalityRequest,
			@CurrentUser UserPrincipal userPrincipal) {

		System.out.println("municipalityRequest..." + municipalityRequest);
		System.out.println("userPrincipal..." + userPrincipal);

		MunicipalityResponse municipalityResponse = municipalityService.createMunicipality(municipalityRequest, userPrincipal);
		return new ResponseEntity<>(municipalityResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{municipality_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteMunicipalityById(
			@PathVariable("municipality_id") Long municipalityId,
			@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = municipalityService.deleteMunicipalityById(municipalityId, userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{municipality_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<MunicipalityResponse> updateMunicipalityById(
			@PathVariable("municipality_id") Long municipalityId,
			@RequestBody MunicipalityRequest municipalityRequest,
			@CurrentUser UserPrincipal userPrincipal) {
		MunicipalityResponse municipalityResponse = municipalityService.updateMunicipalityById(municipalityId, municipalityRequest, userPrincipal);
		return new ResponseEntity<>(municipalityResponse, HttpStatus.OK);
	}

}
