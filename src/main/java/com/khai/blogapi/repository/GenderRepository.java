package com.khai.blogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {

	Optional<Gender> findByName(String name);

	boolean existsByName(String name);
}
