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
	private Long municipalityId;
    private String municipalityName;
	private Long municipalityAddrId;
    private String municipalityAddrName;
}
