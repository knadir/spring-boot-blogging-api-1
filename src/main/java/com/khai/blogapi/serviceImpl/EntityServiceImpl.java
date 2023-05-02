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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.AccessDeniedException;
import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.exception.UserNotFoundException;
import com.khai.blogapi.model.EntityRec;
import com.khai.blogapi.model.RoleName;
import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.EntityRequest;
import com.khai.blogapi.payload.EntityResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.EntityRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.EntityService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class EntityServiceImpl implements EntityService {

	@Autowired
	EntityRepository entityRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse<EntityResponse> getAllEntities(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<EntityRec> entities = entityRepository.findAll(pageable);
		List<EntityResponse> entityResponses = Arrays
				.asList(modelMapper.map(entities.getContent(), EntityResponse[].class));

		PageResponse<EntityResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(entityResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(entities.getNumberOfElements());
		pageResponse.setTotalPages(entities.getTotalPages());
		pageResponse.setLast(entities.isLast());

		return pageResponse;
	}

	@Override
    public List<EntityResponse> getEntities() {
        List<EntityRec> entities = StreamSupport
                .stream(entityRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return mapper.entitiesToEntitiesResponseDtos(entities);
    }

	@Override
	public EntityResponse getEntityById(Long entityId) {
		EntityRec entity = entityRepository.findById(entityId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.ENTITY_NOT_FOUND + entityId));

		return modelMapper.map(entity, EntityResponse.class);
	}

	@Override
	public EntityRec getEntity(Long entityId) {
		return entityRepository.findById(entityId).orElseThrow(() ->
                new IllegalArgumentException("could not find entities with id: " + entityId));
    }

	@Override
	public EntityResponse createEntity(EntityRequest entityRequest, UserPrincipal userPrincipal) {

		EntityRec entity = modelMapper.map(entityRequest, EntityRec.class);

		if (entityRepository.findByName(entity.getName()).isPresent()) {
			throw new ResourceExistException(AppConstant.ENTITY_EXIST);
		}

		entityRepository.save(entity);

		return modelMapper.map(entity, EntityResponse.class);

	}

	@Override
	public ApiResponse deleteEntityById(Long entityId, UserPrincipal userPrincipal) {
		EntityRec entity = entityRepository.findById(entityId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.ENTITY_NOT_FOUND + entityId));

		entityRepository.delete(entity);
		return new ApiResponse(Boolean.TRUE, AppConstant.ENTITY_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteAll() {
		entityRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE, AppConstant.ENTITY_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public EntityResponse updateEntityById(Long entityId, EntityRequest entityRequest,
			UserPrincipal userPrincipal) {

		if (entityRepository.existsByName(entityRequest.getName())) {
			throw new ResourceExistException(AppConstant.ENTITY_EXIST);
		}

		modelMapper.typeMap(EntityRequest.class, EntityRec.class).addMappings(mapper -> mapper.skip(EntityRec::setId));

		EntityRec entity = entityRepository.findById(entityId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.ENTITY_NOT_FOUND + entityId));

		modelMapper.map(entityRequest, entity);

		entityRepository.save(entity);

		return modelMapper.map(entity, EntityResponse.class);

	}
}
