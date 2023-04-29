package com.khai.blogapi.payload;

import lombok.Data;

@Data
public class CountyRequest {
	private String name;
	private Long entityId;
}
