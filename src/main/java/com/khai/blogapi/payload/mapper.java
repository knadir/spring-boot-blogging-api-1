package com.khai.blogapi.payload;

import com.khai.blogapi.payload.EntityResponse;
import com.khai.blogapi.model.EntityRec;

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
}
