package com.khai.blogapi.service;

import com.khai.blogapi.model.Activity;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.ActivityRequest;
import com.khai.blogapi.payload.ActivityResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;
import java.util.List;

public interface ActivityService {
  PageResponse<ActivityResponse> getAllActivities(Integer page, Integer size);

  List<ActivityResponse> getActivities();

  ActivityResponse getActivityById(Long ActivityId);

  Activity getActivity(Long ActivityId);

  ActivityResponse createActivity(ActivityRequest ActivityRequest, UserPrincipal userPrincipal);

  ApiResponse deleteActivityById(Long ActivityId, UserPrincipal userPrincipal);

  ApiResponse deleteAll();

  ActivityResponse updateActivityById(
    Long ActivityId,
    ActivityRequest ActivityRequest,
    UserPrincipal userPrincipal
  );
}
