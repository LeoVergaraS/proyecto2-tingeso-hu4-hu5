package com.app.sueldoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.sueldoservice.entity.Sueldo;

@Repository
public interface SueldoRepository extends JpaRepository<Sueldo, Long>{
}
