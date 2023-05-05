package com.khai.blogapi.payload;

import lombok.Data;

@Data
public class EmployerRequest {
	private String firstName;
	private String lastName;
	private Long municipalityId;
}
