package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.County;
import com.khai.blogapi.model.EntityRec;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.CountyRequest;
import com.khai.blogapi.payload.CountyResponse;
import com.khai.blogapi.payload.EntityResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.CountyRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.CountyService;
import com.khai.blogapi.service.EntityService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class CountyServiceImpl implements CountyService {

	@Autowired
	CountyRepository countyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	EntityService entityService;

	@Override
	public PageResponse<CountyResponse> getAllCounties(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<County> counties = countyRepository.findAll(pageable);
		List<CountyResponse> countyResponses = Arrays
				.asList(modelMapper.map(counties.getContent(), CountyResponse[].class));

		PageResponse<CountyResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(countyResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(counties.getNumberOfElements());
		pageResponse.setTotalPages(counties.getTotalPages());
		pageResponse.setLast(counties.isLast());

		return pageResponse;
	}

	@Override
    public List<CountyResponse> getCounties() {
        List<County> counties = StreamSupport
                .stream(countyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return mapper.countiesToCountyResponse(counties);
    }

	@Override
	public CountyResponse getCountyById(Long countyId) {
		County county = countyRepository.findById(countyId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + countyId));

		return modelMapper.map(county, CountyResponse.class);
	}

	@Override
	public CountyResponse createCounty(CountyRequest countyRequest, UserPrincipal userPrincipal) {

		County county = modelMapper.map(countyRequest, County.class);

		if (countyRepository.findByName(county.getName()).isPresent()) {
			throw new ResourceExistException(AppConstant.COUNTY_EXIST);
		}

		EntityRec entity = entityService.getEntity(countyRequest.getEntityId());
		county.setEntity(entity);

		countyRepository.save(county);

		return modelMapper.map(county, CountyResponse.class);

	}

	@Override
	public ApiResponse deleteCountyById(Long countyId, UserPrincipal userPrincipal) {
		County county = countyRepository.findById(countyId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.COUNTY_NOT_FOUND + countyId));

		countyRepository.delete(county);
		return new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteAll() {
		countyRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public CountyResponse updateCountyById(Long countyId, CountyRequest countyRequest,
			UserPrincipal userPrincipal) {

		if (countyRepository.existsByName(countyRequest.getName())) {
			throw new ResourceExistException(AppConstant.CATEGORY_EXIST);
		}

		modelMapper.typeMap(CountyRequest.class, County.class).addMappings(mapper -> mapper.skip(County::setId));

		County county = countyRepository.findById(countyId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + countyId));

		modelMapper.map(countyRequest, county);

		countyRepository.save(county);

		return modelMapper.map(county, CountyResponse.class);

	}
}
