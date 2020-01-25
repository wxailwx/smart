package com.smart.smart.service;

import com.smart.smart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByNumber(String number);
}
