package com.khai.blogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.County;

@Repository
public interface CountyRepository extends JpaRepository<County, Long> {

	Optional<County> findByName(String name);

	boolean existsByName(String name);

}
