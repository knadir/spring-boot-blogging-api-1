package com.khai.blogapi.repository;

import com.khai.blogapi.model.Department;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
  Optional<Department> findByName(String name);
  boolean existsByName(String name);
  boolean existsByActivityId(Long activityId);
  
}
