package com.khai.blogapi.payload;

import lombok.Data;
import java.util.Date;

@Data
public class EmployerRequest {

  private String idOld;
  private String firstName;
  private String lastName;
  private String fatherName;
  private String identificationNumber;
  private String placeBorn;
  private Date birthday;
  private Date dateOfTermination;
  private Long municipalityBornId;
  private String placeAddr;
  private Long municipalityAddrId;
  private Long genderId;
  private Long qualificationId;
  private String street;
  private String streetNumber;
}
