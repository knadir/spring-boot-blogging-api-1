package com.khai.blogapi.payload;

import com.khai.blogapi.model.County;
import com.khai.blogapi.model.Employer;
import com.khai.blogapi.model.EntityRec;
import com.khai.blogapi.model.Municipality;
import com.khai.blogapi.model.Gender;
import java.util.ArrayList;
import java.util.List;

public class mapper {

  public static EntityResponse entitiesToEntitiesResponseDto(
    EntityRec entities
  ) {
    EntityResponse entitiesResponseDto = new EntityResponse();
    entitiesResponseDto.setId(entities.getId());
    entitiesResponseDto.setName(entities.getName());
    return entitiesResponseDto;
  }

  public static List<EntityResponse> entitiesToEntitiesResponseDtos(
    List<EntityRec> entities
  ) {
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

  public static List<CountyResponse> countiesToCountyResponse(
    List<County> counties
  ) {
    List<CountyResponse> countyResponse = new ArrayList<>();
    for (County county : counties) {
      countyResponse.add(countyToCountyResponse(county));
    }
    return countyResponse;
  }

  public static MunicipalityResponse municipalityToMunicipalityResponse(
    Municipality municipality
  ) {
    MunicipalityResponse municipalityResponse = new MunicipalityResponse();
    municipalityResponse.setId(municipality.getId());
    municipalityResponse.setName(municipality.getName());
    municipalityResponse.setCountyId(municipality.getCounty().getId());
    municipalityResponse.setCountyName(municipality.getCounty().getName());
    return municipalityResponse;
  }

  public static List<MunicipalityResponse> municipalitiesToMunicipalityResponse(
    List<Municipality> municipalities
  ) {
    List<MunicipalityResponse> municipalityResponse = new ArrayList<>();
    for (Municipality municipality : municipalities) {
      municipalityResponse.add(
        municipalityToMunicipalityResponse(municipality)
      );
    }
    return municipalityResponse;
  }

  public static EmployerResponse employerToEmployerResponse(Employer employer) {
    EmployerResponse employerResponse = new EmployerResponse();
    employerResponse.setId(employer.getId());
    employerResponse.setFirstName(employer.getFirstName());
    employerResponse.setLastName(employer.getLastName());
    employerResponse.setFatherName(employer.getFatherName());
    employerResponse.setIdentificationNumber(
      employer.getIdentificationNumber()
    );
    employerResponse.setMunicipalityBornId(
      employer.getMunicipalityBorn().getId()
    );
    employerResponse.setMunicipalityBornName(
      employer.getMunicipalityBorn().getName()
    );
    employerResponse.setPlaceBorn(employer.getPlaceBorn());
    employerResponse.setMunicipalityAddrId(
      employer.getMunicipalityAddr().getId()
    );
    employerResponse.setMunicipalityAddrName(
      employer.getMunicipalityAddr().getName()
    );
    employerResponse.setPlaceAddr(employer.getPlaceAddr());
    employerResponse.setStreet(employer.getStreet());
    employerResponse.setStreetNumber(employer.getStreetNumber());
    return employerResponse;
  }

  public static List<EmployerResponse> employersToEmployerResponse(
    List<Employer> employers
  ) {
    List<EmployerResponse> employerResponse = new ArrayList<>();
    for (Employer employer : employers) {
      employerResponse.add(employerToEmployerResponse(employer));
    }
    return employerResponse;
  }
}
