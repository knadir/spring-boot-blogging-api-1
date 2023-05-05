package com.khai.blogapi.payload;

import lombok.Data;

@Data
public class EmployerRequest {
	private String name;
	private String firstName;
	private Long municipalityId;
}
