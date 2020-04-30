package com.jacob.starter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacob.starter.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
