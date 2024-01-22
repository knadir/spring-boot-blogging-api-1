package com.khai.blogapi.repository;

import com.khai.blogapi.model.Employer;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
  Optional<Employer> findByFirstName(String firstName);
  boolean existsByIdOld(String idOld);
  boolean existsByFirstName(String firstName);
  boolean existsByLastName(String lastName);
  boolean existsByFatherName(String fatherName);
  boolean existsByIdentificationNumber(String identificationNumber);
  boolean existsByIdentNumber(String identNumber);
  boolean existsByMunicipalityBornId(Long municipalityId);
  boolean existsByPlaceBorn(String placeBorn);
  boolean existsByBirthday(Date birthday);
  boolean existsByDateOfTermination(Date dateOfTermination);
  boolean existsByMunicipalityAddrId(Long municipalityId);
  boolean existsByGenderId(Long genderId);
  boolean existsByQualificationId(Long qualificationId);
  boolean existsByBankId(Long bankId);
  boolean existsByPlaceAddr(String placeAddr);
  boolean existsByBankAccount(String bankAccount);
  boolean existsByStreet(String street);
  boolean existsByStreetNumber(String streetNumber);
}
