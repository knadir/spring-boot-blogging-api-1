package com.khai.blogapi.payload;

import java.util.List;

import com.khai.blogapi.model.UserDateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CountyResponse extends UserDateAudit{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Long entityId;
    private String entityName;
    private List<String> municipalityNames;
}
