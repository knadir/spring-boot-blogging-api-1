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
import com.khai.blogapi.model.Qualification;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.QualificationRequest;
import com.khai.blogapi.payload.QualificationResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.QualificationRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.QualificationService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class QualificationServiceImpl implements QualificationService {

	@Autowired
	QualificationRepository qualificationRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse<QualificationResponse> getAllQualifications(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<Qualification> qualifications = qualificationRepository.findAll(pageable);
		List<QualificationResponse> qualificationResponses = Arrays
				.asList(modelMapper.map(qualifications.getContent(), QualificationResponse[].class));

		PageResponse<QualificationResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(qualificationResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(qualifications.getNumberOfElements());
		pageResponse.setTotalPages(qualifications.getTotalPages());
		pageResponse.setLast(qualifications.isLast());

		return pageResponse;
	}

	@Override
	public List<QualificationResponse> getQualifications() {
		List<Qualification> qualifications = StreamSupport
				.stream(qualificationRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return mapper.qualificationsToQualificationResponse(qualifications);
	}

	@Override
	public QualificationResponse getQualificationById(Long qualificationId) {
		Qualification qualification = qualificationRepository.findById(qualificationId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.QUALIFICATION_NOT_FOUND + qualificationId));				
		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// return modelMapper.map(qualification, QualificationResponse.class);
		return mapper.qualificationToQualificationResponse(qualification);
	}

	@Override
	public Qualification getQualification(Long qualificationId) {
		return qualificationRepository.findById(qualificationId)
				.orElseThrow(
						() -> new IllegalArgumentException("could not find qualifications with id: " + qualificationId));
	}

	@Override
	public QualificationResponse createQualification(QualificationRequest qualificationRequest,
			UserPrincipal userPrincipal) {

		Qualification qualification = modelMapper.map(qualificationRequest, Qualification.class);

		if (qualificationRepository.findByName(qualification.getName()).isPresent()) {
			throw new ResourceExistException(AppConstant.QUALIFICATION_EXIST);
		}

		qualificationRepository.save(qualification);

		return modelMapper.map(qualification, QualificationResponse.class);

	}

	@Override
	public ApiResponse deleteQualificationById(Long qualificationId, UserPrincipal userPrincipal) {
		Qualification qualification = qualificationRepository.findById(qualificationId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.QUALIFICATION_NOT_FOUND + qualificationId));

		qualificationRepository.delete(qualification);
		return new ApiResponse(Boolean.TRUE, AppConstant.QUALIFICATION_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteAll() {
		qualificationRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE, AppConstant.QUALIFICATION_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public QualificationResponse updateQualificationById(Long qualificationId, QualificationRequest qualificationRequest,
			UserPrincipal userPrincipal) {

		// if (qualificationRepository.existsByName(qualificationRequest.getName())) {
		// throw new ResourceExistException(AppConstant.QUALIFICATION_EXIST);
		// }

		modelMapper.typeMap(QualificationRequest.class, Qualification.class)
				.addMappings(mapper -> mapper.skip(Qualification::setId));

		Qualification qualification = qualificationRepository.findById(qualificationId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.QUALIFICATION_NOT_FOUND + qualificationId));

		modelMapper.map(qualificationRequest, qualification);

		qualificationRepository.save(qualification);

		return modelMapper.map(qualification, QualificationResponse.class);

	}
}
