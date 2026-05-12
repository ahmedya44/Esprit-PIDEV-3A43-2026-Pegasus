package com.pegasus.services;

public class LoginService {

    private static final String ARTISTE_USERNAME = "artiste";
    private static final String USER_USERNAME = "user";
    private static final String PASSWORD = "test";

    public String login(String username, String password) {
        if (!password.equals(PASSWORD)) {
            return null;
        }
        if (username.equals(ARTISTE_USERNAME)) {
            return "ROLE_ARTISTE";
        } else if (username.equals(USER_USERNAME)) {
            return "ROLE_USER";
        }
        return null;
    }
}