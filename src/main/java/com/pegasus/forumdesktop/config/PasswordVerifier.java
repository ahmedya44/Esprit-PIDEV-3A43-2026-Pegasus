package com.pegasus.forumdesktop.config;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordVerifier {
    private PasswordVerifier() {
    }

    public static boolean verify(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null || storedHash.isBlank()) {
            return false;
        }

        String normalizedHash = storedHash.startsWith("$2y$")
            ? "$2a$" + storedHash.substring(4)
            : storedHash;

        try {
            return BCrypt.checkpw(plainPassword, normalizedHash);
        } catch (IllegalArgumentException ex) {
            return plainPassword.equals(storedHash);
        }
    }
}
