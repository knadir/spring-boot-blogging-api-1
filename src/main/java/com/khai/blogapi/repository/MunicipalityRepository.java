package com.khai.blogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Municipality;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

	Optional<Municipality> findByName(String name);

	boolean existsByName(String name);

}
