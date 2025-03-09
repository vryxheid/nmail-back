package com.branching.nmail.repository;

import com.branching.nmail.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
}
