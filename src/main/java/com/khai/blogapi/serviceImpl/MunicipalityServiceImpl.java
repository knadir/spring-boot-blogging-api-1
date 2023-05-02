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
import com.khai.blogapi.model.Municipality;
import com.khai.blogapi.model.County;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.MunicipalityRequest;
import com.khai.blogapi.payload.MunicipalityResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.MunicipalityRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.MunicipalityService;
import com.khai.blogapi.service.CountyService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class MunicipalityServiceImpl implements MunicipalityService {

	@Autowired
	MunicipalityRepository municipalityRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CountyService countyService;

	@Override
	public PageResponse<MunicipalityResponse> getAllMunicipalities(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<Municipality> municipalities = municipalityRepository.findAll(pageable);
		List<MunicipalityResponse> municipalityResponses = Arrays
				.asList(modelMapper.map(municipalities.getContent(), MunicipalityResponse[].class));

		PageResponse<MunicipalityResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(municipalityResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(municipalities.getNumberOfElements());
		pageResponse.setTotalPages(municipalities.getTotalPages());
		pageResponse.setLast(municipalities.isLast());

		return pageResponse;
	}

	@Override
    public List<MunicipalityResponse> getMunicipalities() {
        List<Municipality> municipalities = StreamSupport
                .stream(municipalityRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return mapper.municipalitiesToMunicipalityResponse(municipalities);
    }

	@Override
	public MunicipalityResponse getMunicipalityById(Long municipalityId) {
		Municipality municipality = municipalityRepository.findById(municipalityId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.MUNICIPALITY_NOT_FOUND + municipalityId));

		return modelMapper.map(municipality, MunicipalityResponse.class);
	}

	@Override
	public MunicipalityResponse createMunicipality(MunicipalityRequest municipalityRequest, UserPrincipal userPrincipal) {

		Municipality municipality = modelMapper.map(municipalityRequest, Municipality.class);

		if (municipalityRepository.findByName(municipality.getName()).isPresent()) {
			throw new ResourceExistException(AppConstant.COUNTY_EXIST);
		}

		County county = countyService.getCounty(municipalityRequest.getCountyId());
		municipality.setCounty(county);

		municipalityRepository.save(municipality);

		return modelMapper.map(municipality, MunicipalityResponse.class);

	}

	@Override
	public ApiResponse deleteMunicipalityById(Long municipalityId, UserPrincipal userPrincipal) {
		Municipality municipality = municipalityRepository.findById(municipalityId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.COUNTY_NOT_FOUND + municipalityId));

		municipalityRepository.delete(municipality);
		return new ApiResponse(Boolean.TRUE, AppConstant.MUNICIPALITY_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteAll() {
		municipalityRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE, AppConstant.MUNICIPALITY_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public MunicipalityResponse updateMunicipalityById(Long municipalityId, MunicipalityRequest municipalityRequest,
			UserPrincipal userPrincipal) {

		if (municipalityRepository.existsByName(municipalityRequest.getName())) {
			throw new ResourceExistException(AppConstant.MUNICIPALITY_EXIST);
		}

		modelMapper.typeMap(MunicipalityRequest.class, Municipality.class).addMappings(mapper -> mapper.skip(Municipality::setId));

		Municipality municipality = municipalityRepository.findById(municipalityId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.MUNICIPALITY_NOT_FOUND + municipalityId));

		modelMapper.map(municipalityRequest, municipality);

		municipalityRepository.save(municipality);

		return modelMapper.map(municipality, MunicipalityResponse.class);

	}
}
