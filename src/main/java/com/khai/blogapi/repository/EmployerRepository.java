package com.khai.blogapi.repository;

import com.khai.blogapi.model.Employer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
  Optional<Employer> findByFirstName(String firstName);
  boolean existsByFirstName(String firstName);
  boolean existsByLastName(String lastName);
  boolean existsByFatherName(String fatherName);
  boolean existsByIdentificationNumber(String identificationNumber);
  boolean existsByMunicipalityBornId(Long municipalityId);
  boolean existsByPlaceBorn(String placeBorn);
  boolean existsByMunicipalityAddrId(Long municipalityId);
  boolean existsByPlaceAddr(String placeAddr);
  boolean existsByStreet(String street);
  boolean existsByStreetNumber(String streetNumber);
  boolean existsByGenderId(Long genderId);  
}
