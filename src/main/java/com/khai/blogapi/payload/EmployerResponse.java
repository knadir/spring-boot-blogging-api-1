package com.khai.blogapi.payload;

import com.khai.blogapi.model.UserDateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployerResponse extends UserDateAudit {

  private static final long serialVersionUID = 1L;
  private Long id;
  private String idOld;
  private String firstName;
  private String lastName;
  private String fatherName;
  private String identificationNumber;
  private Long municipalityBornId;
  private String municipalityBornName;
  private String placeBorn;
  private Date birthday;
  private Date dateOfTermination;
  private Long municipalityAddrId;
  private String municipalityAddrName;
  private Long genderId;
  private String genderName;
  private Long qualificationId;
  private String qualificationName;
  private String placeAddr;
  private String street;
  private String streetNumber;
}
