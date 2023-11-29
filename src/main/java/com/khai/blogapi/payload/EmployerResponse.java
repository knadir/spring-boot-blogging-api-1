package com.khai.blogapi.payload;

import com.khai.blogapi.model.UserDateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployerResponse extends UserDateAudit{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private String fatherName;
	private String identificationNumber;
	private Long municipalityBornId;
    private String municipalityBornName;
	private String placeBorn;
	private Long municipalityAddrId;
    private String municipalityAddrName;
	private String placeAddr;	
}
