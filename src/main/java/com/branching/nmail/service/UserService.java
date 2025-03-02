package com.branching.nmail.service;

import com.branching.nmail.model.User;
import com.branching.nmail.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<User> getUsers() {
        return userDao.findAll();
    }

    public User getUserById(int id) {
        return userDao.findById(id)
                .orElse(null);

    }

//    public User getUserByEmail(String email) {
//        return userDao.findByEmail(email)
//                .orElse(null);
//
//    }

    public void deleteUserById(int id) {
        userDao.deleteById(id);
    }

    public User saveUser(User user) {
        return userDao.save(user);
    }
}
