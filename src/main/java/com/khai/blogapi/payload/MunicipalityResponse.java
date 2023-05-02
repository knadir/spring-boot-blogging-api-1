package com.khai.blogapi.payload;

import com.khai.blogapi.model.UserDateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MunicipalityResponse extends UserDateAudit{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Long countyId;
    private String countyName;
}
