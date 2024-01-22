package com.khai.blogapi.payload;

import com.khai.blogapi.model.Bank;
import com.khai.blogapi.model.County;
import com.khai.blogapi.model.Employer;
import com.khai.blogapi.model.EntityRec;
import com.khai.blogapi.model.Gender;
import com.khai.blogapi.model.Municipality;
import com.khai.blogapi.model.Qualification;
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

  public static GenderResponse genderToGenderResponse(Gender gender) {
    GenderResponse genderResponse = new GenderResponse();
    genderResponse.setId(gender.getId());
    genderResponse.setName(gender.getName());
    return genderResponse;
  }

  public static List<GenderResponse> gendersToGenderResponse(
    List<Gender> genders
  ) {
    List<GenderResponse> genderResponse = new ArrayList<>();
    for (Gender gender : genders) {
      genderResponse.add(genderToGenderResponse(gender));
    }
    return genderResponse;
  }

  public static QualificationResponse qualificationToQualificationResponse(
    Qualification qualification
  ) {
    QualificationResponse qualificationResponse = new QualificationResponse();
    qualificationResponse.setId(qualification.getId());
    qualificationResponse.setName(qualification.getName());
    return qualificationResponse;
  }

  public static List<QualificationResponse> qualificationsToQualificationResponse(
    List<Qualification> qualifications
  ) {
    List<QualificationResponse> qualificationResponse = new ArrayList<>();
    for (Qualification qualification : qualifications) {
      qualificationResponse.add(
        qualificationToQualificationResponse(qualification)
      );
    }
    return qualificationResponse;
  }

  public static BankResponse bankToBankResponse(Bank bank) {
    BankResponse bankResponse = new BankResponse();
    bankResponse.setId(bank.getId());
    bankResponse.setName(bank.getName());
    return bankResponse;
  }

  public static List<BankResponse> banksToBankResponse(List<Bank> banks) {
    List<BankResponse> bankResponse = new ArrayList<>();
    for (Bank bank : banks) {
      bankResponse.add(bankToBankResponse(bank));
    }
    return bankResponse;
  }

  public static EmployerResponse employerToEmployerResponse(Employer employer) {
    EmployerResponse employerResponse = new EmployerResponse();
    employerResponse.setId(employer.getId());
    employerResponse.setIdOld(employer.getIdOld());
    employerResponse.setFirstName(employer.getFirstName());
    employerResponse.setLastName(employer.getLastName());
    employerResponse.setFatherName(employer.getFatherName());
    employerResponse.setIdentificationNumber(
      employer.getIdentificationNumber()
    );
    employerResponse.setIdentNumber(employer.getIdentNumber());
    if (employer != null && employer.getMunicipalityBorn() != null) {
      employerResponse.setMunicipalityBornId(
        employer.getMunicipalityBorn().getId()
      );
    }
    if (employer != null && employer.getMunicipalityBorn() != null) {
      employerResponse.setMunicipalityBornName(
        employer.getMunicipalityBorn().getName()
      );
    }
    employerResponse.setPlaceBorn(employer.getPlaceBorn());
    employerResponse.setBirthday(employer.getBirthday());
    employerResponse.setDateOfTermination(employer.getDateOfTermination());
    employerResponse.setMunicipalityAddrId(
      employer.getMunicipalityAddr().getId()
    );
    employerResponse.setMunicipalityAddrName(
      employer.getMunicipalityAddr().getName()
    );
    employerResponse.setGenderId(employer.getGender().getId());
    employerResponse.setGenderName(employer.getGender().getName());
    if (employer != null && employer.getQualification() != null) {
      employerResponse.setQualificationId(employer.getQualification().getId());
    }
    if (employer != null && employer.getQualification() != null) {
      employerResponse.setQualificationName(
        employer.getQualification().getName()
      );
    }
    if (employer != null && employer.getBank() != null) {
      employerResponse.setBankId(employer.getBank().getId());
    }
    if (employer != null && employer.getBank() != null) {
      employerResponse.setBankName(employer.getBank().getName());
    }
    employerResponse.setBankAccount(employer.getBankAccount());
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
