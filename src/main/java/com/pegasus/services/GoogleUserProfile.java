package com.pegasus.services;

public record GoogleUserProfile(
        String sub,
        String email,
        boolean emailVerified,
        String name,
        String givenName,
        String familyName,
        String pictureUrl
) {
}
