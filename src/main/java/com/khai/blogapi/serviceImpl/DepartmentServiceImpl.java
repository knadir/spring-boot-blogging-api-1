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
import com.khai.blogapi.model.Department;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.DepartmentRequest;
import com.khai.blogapi.payload.DepartmentResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.DepartmentRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.DepartmentService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse<DepartmentResponse> getAllDepartments(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<Department> departments = departmentRepository.findAll(pageable);
		List<DepartmentResponse> departmentResponses = Arrays
				.asList(modelMapper.map(departments.getContent(), DepartmentResponse[].class));

		PageResponse<DepartmentResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(departmentResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(departments.getNumberOfElements());
		pageResponse.setTotalPages(departments.getTotalPages());
		pageResponse.setLast(departments.isLast());

		return pageResponse;
	}

	@Override
	public List<DepartmentResponse> getDepartments() {
		List<Department> departments = StreamSupport
				.stream(departmentRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return mapper.departmentsToDepartmentResponse(departments);
	}

	@Override
	public DepartmentResponse getDepartmentById(Long departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.DEPARTMENT_NOT_FOUND + departmentId));				
		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// return modelMapper.map(department, DepartmentResponse.class);
		return mapper.departmentToDepartmentResponse(department);
	}

	@Override
	public Department getDepartment(Long departmentId) {
		return departmentRepository.findById(departmentId)
				.orElseThrow(
						() -> new IllegalArgumentException("could not find departments with id: " + departmentId));
	}

	@Override
	public DepartmentResponse createDepartment(DepartmentRequest departmentRequest,
			UserPrincipal userPrincipal) {

		Department department = modelMapper.map(departmentRequest, Department.class);

		if (departmentRepository.findByName(department.getName()).isPresent()) {
			throw new ResourceExistException(AppConstant.DEPARTMENT_EXIST);
		}

		departmentRepository.save(department);

		return modelMapper.map(department, DepartmentResponse.class);

	}

	@Override
	public ApiResponse deleteDepartmentById(Long departmentId, UserPrincipal userPrincipal) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.DEPARTMENT_NOT_FOUND + departmentId));

		departmentRepository.delete(department);
		return new ApiResponse(Boolean.TRUE, AppConstant.DEPARTMENT_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteAll() {
		departmentRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE, AppConstant.DEPARTMENT_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public DepartmentResponse updateDepartmentById(Long departmentId, DepartmentRequest departmentRequest,
			UserPrincipal userPrincipal) {

		// if (departmentRepository.existsByName(departmentRequest.getName())) {
		// throw new ResourceExistException(AppConstant.DEPARTMENT_EXIST);
		// }

		modelMapper.typeMap(DepartmentRequest.class, Department.class)
				.addMappings(mapper -> mapper.skip(Department::setId));

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.DEPARTMENT_NOT_FOUND + departmentId));

		modelMapper.map(departmentRequest, department);

		departmentRepository.save(department);

		return modelMapper.map(department, DepartmentResponse.class);

	}
}
