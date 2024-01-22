package com.khai.blogapi.payload;

import java.util.Date;
import lombok.Data;

@Data
public class EmployerRequest {

  private String idOld;
  private String firstName;
  private String lastName;
  private String fatherName;
  private String identificationNumber;
  private String identNumber;
  private String placeBorn;
  private Date birthday;
  private Date dateOfTermination;
  private Long municipalityBornId;
  private String placeAddr;
  private Long municipalityAddrId;
  private Long genderId;
  private Long qualificationId;
  private Long bankId;
  private String bankAccount;
  private String street;
  private String streetNumber;
}
