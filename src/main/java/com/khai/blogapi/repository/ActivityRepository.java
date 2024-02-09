package com.khai.blogapi.repository;

import com.khai.blogapi.model.Activity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
  Optional<Activity> findByName(String name);

  boolean existsByName(String name);
  boolean existsByIdOld(String idOld);
}
