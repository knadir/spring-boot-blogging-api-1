package com.khai.blogapi.payload;

import lombok.Data;

@Data
public class EmployerRequest {

  private String firstName;
  private String lastName;
  private String fatherName;
  private String identificationNumber;
  private String placeBorn;
  private Long municipalityBornId;
  private String placeAddr;
  private Long municipalityAddrId;
  private String street;
  private String streetNumber;
}
