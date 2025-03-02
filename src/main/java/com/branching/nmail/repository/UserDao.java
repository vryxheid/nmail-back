package com.branching.nmail.repository;

import com.branching.nmail.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository<User, Integer> {
//    public Optional<User> findByEmail(String email);
//    public int logInAttempt(String email, String password);
}
