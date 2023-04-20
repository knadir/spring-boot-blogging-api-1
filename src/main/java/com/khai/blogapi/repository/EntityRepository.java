package com.khai.blogapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.EntityRec;
import com.khai.blogapi.model.User;

@Repository
public interface EntityRepository extends JpaRepository<EntityRec, Long> {

	Optional<EntityRec> findByName(String name);

	boolean existsByName(String name);

}
