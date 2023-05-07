package com.khai.blogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

	Optional<Employer> findByFirstName(String firstName);
	boolean existsByFirstName(String firstName);
	boolean existsByLastName(String lastName);
	boolean existsByMunicipalityId(Long municipalityIf);
	boolean existsByMunicipalityAddrId(Long municipalityIf);

}
