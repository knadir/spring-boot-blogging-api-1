package com.khai.blogapi.repository;

import com.khai.blogapi.model.CostPlace;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostPlaceRepository extends JpaRepository<CostPlace, Long> {
  Optional<CostPlace> findByName(String name);
  boolean existsByName(String name);
}
