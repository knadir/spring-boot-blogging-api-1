package com.khai.blogapi.serviceImpl;

import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Activity;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.ActivityRequest;
import com.khai.blogapi.payload.ActivityResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.mapper;
import com.khai.blogapi.repository.ActivityRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.ActivityService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;
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

@Service
public class ActivityServiceImpl implements ActivityService {

  @Autowired
  ActivityRepository activityRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ModelMapper modelMapper;

  @Override
  public PageResponse<ActivityResponse> getAllActivities(Integer page, Integer size) {
    AppUtils.validatePageAndSize(page, size);
    Pageable pageable = PageRequest.of(page, size);

    Page<Activity> activities = activityRepository.findAll(pageable);
    List<ActivityResponse> activityResponses = Arrays.asList(
      modelMapper.map(activities.getContent(), ActivityResponse[].class)
    );

    PageResponse<ActivityResponse> pageResponse = new PageResponse<>();
    pageResponse.setContent(activityResponses);
    pageResponse.setPage(page);
    pageResponse.setSize(size);
    pageResponse.setTotalElements(activities.getNumberOfElements());
    pageResponse.setTotalPages(activities.getTotalPages());
    pageResponse.setLast(activities.isLast());

    return pageResponse;
  }

  @Override
  public List<ActivityResponse> getActivities() {
    List<Activity> activities = StreamSupport
      .stream(activityRepository.findAll().spliterator(), false)
      .collect(Collectors.toList());
    return mapper.activitiesToActivityResponse(activities);
  }

  @Override
  public ActivityResponse getActivityById(Long activityId) {
    Activity activity = activityRepository
      .findById(activityId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          AppConstant.QUALIFICATION_NOT_FOUND + activityId
        )
      );
    // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    // return modelMapper.map(activity, ActivityResponse.class);
    return mapper.activityToActivityResponse(activity);
  }

  @Override
  public Activity getActivity(Long activityId) {
    return activityRepository
      .findById(activityId)
      .orElseThrow(() ->
        new IllegalArgumentException("could not find activities with id: " + activityId)
      );
  }

  @Override
  public ActivityResponse createActivity(
    ActivityRequest activityRequest,
    UserPrincipal userPrincipal
  ) {
    Activity activity = modelMapper.map(activityRequest, Activity.class);

    if (activityRepository.findByName(activity.getName()).isPresent()) {
      throw new ResourceExistException(AppConstant.QUALIFICATION_EXIST);
    }

    activityRepository.save(activity);

    return modelMapper.map(activity, ActivityResponse.class);
  }

  @Override
  public ApiResponse deleteActivityById(Long activityId, UserPrincipal userPrincipal) {
    Activity activity = activityRepository
      .findById(activityId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          AppConstant.QUALIFICATION_NOT_FOUND + activityId
        )
      );

    activityRepository.delete(activity);
    return new ApiResponse(
      Boolean.TRUE,
      AppConstant.QUALIFICATION_DELETE_MESSAGE,
      HttpStatus.OK
    );
  }

  @Override
  public ApiResponse deleteAll() {
    activityRepository.deleteAll();
    return new ApiResponse(
      Boolean.TRUE,
      AppConstant.QUALIFICATION_DELETE_MESSAGE,
      HttpStatus.OK
    );
  }

  @Override
  public ActivityResponse updateActivityById(
    Long activityId,
    ActivityRequest activityRequest,
    UserPrincipal userPrincipal
  ) {
    // if (activityRepository.existsByName(activityRequest.getName())) {
    // throw new ResourceExistException(AppConstant.QUALIFICATION_EXIST);
    // }

    modelMapper
      .typeMap(ActivityRequest.class, Activity.class)
      .addMappings(mapper -> mapper.skip(Activity::setId));

    Activity activity = activityRepository
      .findById(activityId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          AppConstant.QUALIFICATION_NOT_FOUND + activityId
        )
      );

    modelMapper.map(activityRequest, activity);

    activityRepository.save(activity);

    return modelMapper.map(activity, ActivityResponse.class);
  }
}
