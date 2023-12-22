package com.khai.blogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Qualification;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {

	Optional<Qualification> findByName(String name);

	boolean existsByName(String name);
}
