package com.khai.blogapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.County;
import com.khai.blogapi.model.EntityRec;

@Repository
public interface CountyRepository extends JpaRepository<County, Long> {

	Optional<County> findByName(String name);

	List<County> findByEntity(EntityRec entity);

	boolean existsByName(String name);

}
