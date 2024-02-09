package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.CostPlace;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.CostPlaceRequest;
import com.khai.blogapi.payload.CostPlaceResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.CostPlaceRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.CostPlaceService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class CostPlaceServiceImpl implements CostPlaceService {

	@Autowired
	CostPlaceRepository costPlaceRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse<CostPlaceResponse> getAllCostPlaces(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<CostPlace> costPlaces = costPlaceRepository.findAll(pageable);
		List<CostPlaceResponse> costPlaceResponses = Arrays
				.asList(modelMapper.map(costPlaces.getContent(), CostPlaceResponse[].class));

		PageResponse<CostPlaceResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(costPlaceResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(costPlaces.getNumberOfElements());
		pageResponse.setTotalPages(costPlaces.getTotalPages());
		pageResponse.setLast(costPlaces.isLast());

		return pageResponse;
	}

	@Override
	public List<CostPlaceResponse> getCostPlaces() {
		List<CostPlace> costPlaces = StreamSupport
				.stream(costPlaceRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return mapper.costPlacesToCostPlaceResponse(costPlaces);
	}

	@Override
	public CostPlaceResponse getCostPlaceById(Long costPlaceId) {
		CostPlace costPlace = costPlaceRepository.findById(costPlaceId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.COST_PLACE_NOT_FOUND + costPlaceId));				
		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// return modelMapper.map(costPlace, CostPlaceResponse.class);
		return mapper.costPlaceToCostPlaceResponse(costPlace);
	}

	@Override
	public CostPlace getCostPlace(Long costPlaceId) {
		return costPlaceRepository.findById(costPlaceId)
				.orElseThrow(
						() -> new IllegalArgumentException("could not find costPlaces with id: " + costPlaceId));
	}

	@Override
	public CostPlaceResponse createCostPlace(CostPlaceRequest costPlaceRequest,
			UserPrincipal userPrincipal) {

		CostPlace costPlace = modelMapper.map(costPlaceRequest, CostPlace.class);

		if (costPlaceRepository.findByName(costPlace.getName()).isPresent()) {
			throw new ResourceExistException(AppConstant.COST_PLACE_EXIST);
		}

		costPlaceRepository.save(costPlace);

		return modelMapper.map(costPlace, CostPlaceResponse.class);

	}

	@Override
	public ApiResponse deleteCostPlaceById(Long costPlaceId, UserPrincipal userPrincipal) {
		CostPlace costPlace = costPlaceRepository.findById(costPlaceId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.COST_PLACE_NOT_FOUND + costPlaceId));

		costPlaceRepository.delete(costPlace);
		return new ApiResponse(Boolean.TRUE, AppConstant.COST_PLACE_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteAll() {
		costPlaceRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE, AppConstant.COST_PLACE_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public CostPlaceResponse updateCostPlaceById(Long costPlaceId, CostPlaceRequest costPlaceRequest,
			UserPrincipal userPrincipal) {

		// if (costPlaceRepository.existsByName(costPlaceRequest.getName())) {
		// throw new ResourceExistException(AppConstant.COST_PLACE_EXIST);
		// }

		modelMapper.typeMap(CostPlaceRequest.class, CostPlace.class)
				.addMappings(mapper -> mapper.skip(CostPlace::setId));

		CostPlace costPlace = costPlaceRepository.findById(costPlaceId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.COST_PLACE_NOT_FOUND + costPlaceId));

		modelMapper.map(costPlaceRequest, costPlace);

		costPlaceRepository.save(costPlace);

		return modelMapper.map(costPlace, CostPlaceResponse.class);

	}
}
