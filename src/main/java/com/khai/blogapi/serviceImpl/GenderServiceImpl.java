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
import com.khai.blogapi.model.Gender;
import com.khai.blogapi.model.County;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.GenderRequest;
import com.khai.blogapi.payload.GenderResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.GenderRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.GenderService;
import com.khai.blogapi.service.CountyService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class GenderServiceImpl implements GenderService {

	@Autowired
	GenderRepository genderRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CountyService countyService;

	@Override
	public PageResponse<GenderResponse> getAllGenders(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<Gender> genders = genderRepository.findAll(pageable);
		List<GenderResponse> genderResponses = Arrays
				.asList(modelMapper.map(genders.getContent(), GenderResponse[].class));

		PageResponse<GenderResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(genderResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(genders.getNumberOfElements());
		pageResponse.setTotalPages(genders.getTotalPages());
		pageResponse.setLast(genders.isLast());

		return pageResponse;
	}

	@Override
	public List<GenderResponse> getGenders() {
		List<Gender> genders = StreamSupport
				.stream(genderRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return mapper.gendersToGenderResponse(genders);
	}

	@Override
	public GenderResponse getGenderById(Long genderId) {
		Gender gender = genderRepository.findById(genderId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.GENDER_NOT_FOUND + genderId));				
		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// return modelMapper.map(gender, GenderResponse.class);
		return mapper.genderToGenderResponse(gender);
	}

	@Override
	public Gender getGender(Long genderId) {
		return genderRepository.findById(genderId)
				.orElseThrow(
						() -> new IllegalArgumentException("could not find genders with id: " + genderId));
	}

	@Override
	public GenderResponse createGender(GenderRequest genderRequest,
			UserPrincipal userPrincipal) {

		Gender gender = modelMapper.map(genderRequest, Gender.class);

		if (genderRepository.findByName(gender.getName()).isPresent()) {
			throw new ResourceExistException(AppConstant.GENDER_EXIST);
		}

		genderRepository.save(gender);

		return modelMapper.map(gender, GenderResponse.class);

	}

	@Override
	public ApiResponse deleteGenderById(Long genderId, UserPrincipal userPrincipal) {
		Gender gender = genderRepository.findById(genderId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.GENDER_NOT_FOUND + genderId));

		genderRepository.delete(gender);
		return new ApiResponse(Boolean.TRUE, AppConstant.GENDER_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteAll() {
		genderRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE, AppConstant.GENDER_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public GenderResponse updateGenderById(Long genderId, GenderRequest genderRequest,
			UserPrincipal userPrincipal) {

		// if (genderRepository.existsByName(genderRequest.getName())) {
		// throw new ResourceExistException(AppConstant.GENDER_EXIST);
		// }

		modelMapper.typeMap(GenderRequest.class, Gender.class)
				.addMappings(mapper -> mapper.skip(Gender::setId));

		Gender gender = genderRepository.findById(genderId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.GENDER_NOT_FOUND + genderId));

		modelMapper.map(genderRequest, gender);

		genderRepository.save(gender);

		return modelMapper.map(gender, GenderResponse.class);

	}
}
