package com.khai.blogapi.controller;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.ActivityRequest;
import com.khai.blogapi.payload.ActivityResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.ActivityService;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.utils.AppConstant;
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

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/activities")
public class ActivityController {

  @Autowired
  ActivityService activityService;

  @Autowired
  BlogService blogService;

  @GetMapping
  public ResponseEntity<PageResponse<ActivityResponse>> getAllActivities(
    @RequestParam(
      value = "page",
      defaultValue = AppConstant.DEFAULT_PAGE_NUMBER
    ) Integer page,
    @RequestParam(
      value = "size",
      defaultValue = AppConstant.DEFAULT_PAGE_SIZE
    ) Integer size
  ) {
    PageResponse<ActivityResponse> activityResponse = activityService.getAllActivities(
      page,
      size
    );
    return new ResponseEntity<>(activityResponse, HttpStatus.OK);
  }

  @GetMapping("/getAll")
  public ResponseEntity<List<ActivityResponse>> getActivities() {
    List<ActivityResponse> activityResponse = activityService.getActivities();
    return new ResponseEntity<>(activityResponse, HttpStatus.OK);
  }

  @GetMapping("/{activity_id}")
  public ResponseEntity<ActivityResponse> getActivityById(
    @PathVariable("activity_id") Long activityId
  ) {
    ActivityResponse activityResponse = activityService.getActivityById(activityId);
    return new ResponseEntity<>(activityResponse, HttpStatus.OK);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  public ResponseEntity<ActivityResponse> createActivity(
    @RequestBody final ActivityRequest activityRequest,
    @CurrentUser UserPrincipal userPrincipal
  ) {
    //System.out.println("activityRequest..." + activityRequest);
    //System.out.println("userPrincipal..." + userPrincipal);

    ActivityResponse activityResponse = activityService.createActivity(
      activityRequest,
      userPrincipal
    );
    return new ResponseEntity<>(activityResponse, HttpStatus.CREATED);
  }

  @DeleteMapping("/{activity_id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  public ResponseEntity<ApiResponse> deleteActivityById(
    @PathVariable("activity_id") Long activityId,
    @CurrentUser UserPrincipal userPrincipal
  ) {
    ApiResponse response = activityService.deleteActivityById(activityId, userPrincipal);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/{activity_id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  public ResponseEntity<ActivityResponse> updateActivityById(
    @PathVariable("activity_id") Long activityId,
    @RequestBody ActivityRequest activityRequest,
    @CurrentUser UserPrincipal userPrincipal
  ) {
    ActivityResponse activityResponse = activityService.updateActivityById(
      activityId,
      activityRequest,
      userPrincipal
    );
    return new ResponseEntity<>(activityResponse, HttpStatus.OK);
  }
}
