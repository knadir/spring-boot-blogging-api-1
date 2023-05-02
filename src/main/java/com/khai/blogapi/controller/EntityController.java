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
import com.khai.blogapi.payload.BlogResponse;
import com.khai.blogapi.payload.EntityRequest;
import com.khai.blogapi.payload.EntityResponse;
import com.khai.blogapi.payload.CountyResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.EntityService;
import com.khai.blogapi.service.CountyService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/entities")
public class EntityController {

	@Autowired
	EntityService entityService;

	@Autowired
	CountyService countyService;

	@Autowired
	BlogService blogService;

	@GetMapping
	public ResponseEntity<PageResponse<EntityResponse>> getAllEntities(
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<EntityResponse> entityResponse = entityService.getAllEntities(page, size);
		return new ResponseEntity<>(entityResponse, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EntityResponse>> getEntities() {
		List<EntityResponse> entityResponse = entityService.getEntities();
		return new ResponseEntity<>(entityResponse, HttpStatus.OK);
	}

	@GetMapping("/{entity_id}")
	public ResponseEntity<EntityResponse> getEntityById(
			@PathVariable("entity_id") Long entityId){
		EntityResponse entityResponse = entityService.getEntityById(entityId);
		return new ResponseEntity<>(entityResponse,HttpStatus.OK);
	}

	@GetMapping("/{entity_id}/counties")
	public ResponseEntity<List<CountyResponse>> getCountiesByEntity(
			@PathVariable("entity_id") Long entityId){
		List<CountyResponse> countyResponses = 
				countyService.getCountiesByEntity(entityId);
		return new ResponseEntity<>(countyResponses,HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<EntityResponse> createEntity(
			@RequestBody EntityRequest entityRequest,
			@CurrentUser UserPrincipal userPrincipal) {

		System.out.println("entityRequest..." + entityRequest);
		System.out.println("userPrincipal..." + userPrincipal);

		EntityResponse entityResponse = entityService.createEntity(entityRequest, userPrincipal);
		return new ResponseEntity<>(entityResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{entity_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteEntityById(
			@PathVariable("entity_id") Long entityId,
			@CurrentUser UserPrincipal userPrincipal) {
		ApiResponse response = entityService.deleteEntityById(entityId, userPrincipal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{entity_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<EntityResponse> updateEntityById(
			@PathVariable("entity_id") Long entityId,
			@RequestBody EntityRequest entityRequest,
			@CurrentUser UserPrincipal userPrincipal) {
		EntityResponse entityResponse = entityService.updateEntityById(entityId, entityRequest, userPrincipal);
		return new ResponseEntity<>(entityResponse, HttpStatus.OK);
	}

}
