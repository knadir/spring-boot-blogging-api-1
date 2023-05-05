package com.khai.blogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

	Optional<Employer> findByName(String name);
	boolean existsByName(String name);
	boolean existsByFirstName(String firstName);
	boolean existsByMunicipalityId(Long municipalityIf);

}
