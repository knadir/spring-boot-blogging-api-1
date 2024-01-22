package com.khai.blogapi.repository;

import com.khai.blogapi.model.Bank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
  Optional<Bank> findByName(String name);

  boolean existsByName(String name);
}
