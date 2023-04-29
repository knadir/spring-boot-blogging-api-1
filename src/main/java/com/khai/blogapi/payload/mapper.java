package com.khai.blogapi.payload;

import com.khai.blogapi.payload.EntityResponse;
import com.khai.blogapi.model.EntityRec;
import com.khai.blogapi.payload.CountyResponse;
import com.khai.blogapi.model.County;

import java.util.ArrayList;
import java.util.List;

public class mapper {

 
    public static EntityResponse entitiesToEntitiesResponseDto(EntityRec entities) {
        EntityResponse entitiesResponseDto = new EntityResponse();
        entitiesResponseDto.setId(entities.getId());
        entitiesResponseDto.setName(entities.getName());
        return entitiesResponseDto;
    }

    public static List<EntityResponse> entitiesToEntitiesResponseDtos(List<EntityRec> entities) {
        List<EntityResponse> entitiesResponseDtos = new ArrayList<>();
        for (EntityRec entity : entities) {
            entitiesResponseDtos.add(entitiesToEntitiesResponseDto(entity));
        }
        return entitiesResponseDtos;
    }
    public static CountyResponse countyToCountyResponse(County county) {
        CountyResponse countyResponse = new CountyResponse();
        countyResponse.setId(county.getId());
        countyResponse.setName(county.getName());
        countyResponse.setEntityId(county.getEntity().getId());
        countyResponse.setEntityName(county.getEntity().getName());
        return countyResponse;
    }

    public static List<CountyResponse> countiesToCountyResponse(List<County> counties) {
        List<CountyResponse> countyResponse = new ArrayList<>();
        for (County county : counties) {
            countyResponse.add(countyToCountyResponse(county));
        }
        return countyResponse;
    }
}
