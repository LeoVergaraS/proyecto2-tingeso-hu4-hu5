package com.app.sueldoservice.repository;

import org.springframework.stereotype.Repository;

import com.app.sueldoservice.entity.UserData;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    UserData findByUsuario(String usuario);
}
