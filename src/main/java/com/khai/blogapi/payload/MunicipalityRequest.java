package com.khai.blogapi.payload;

import lombok.Data;

@Data
public class MunicipalityRequest {
	private String name;
	private Long countyId;
}
