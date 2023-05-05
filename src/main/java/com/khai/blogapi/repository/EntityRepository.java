package com.khai.blogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.EntityRec;

@Repository
public interface EntityRepository extends JpaRepository<EntityRec, Long> {

	Optional<EntityRec> findByName(String name);

	boolean existsByName(String name);
}
