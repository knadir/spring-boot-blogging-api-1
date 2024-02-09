package com.khai.blogapi.payload;

import com.khai.blogapi.model.UserDateAudit;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentResponse extends UserDateAudit {

  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private Long activityId;
  private String activityName;
  private String activityIdOld;
  
}
