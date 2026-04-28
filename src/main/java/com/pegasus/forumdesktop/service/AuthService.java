package com.pegasus.forumdesktop.service;

import com.pegasus.forumdesktop.config.PasswordVerifier;
import com.pegasus.forumdesktop.dao.UserDao;
import com.pegasus.forumdesktop.model.User;

import java.util.Optional;

public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> authenticate(String email, String password) {
        return userDao.findByEmail(email)
            .filter(user -> PasswordVerifier.verify(password, user.getPassword()));
    }
}
