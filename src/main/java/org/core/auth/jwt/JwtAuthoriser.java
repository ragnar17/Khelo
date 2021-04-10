package org.core.auth.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.auth.Authorizer;


public class JwtAuthoriser implements Authorizer<ExampleUser> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthoriser.class);

    @Override
    public boolean authorize(ExampleUser exampleUser, String requiredRole) {
        if (exampleUser == null) {
            LOGGER.warn("msg=user object was null");
            return false;
        }

        String roles = exampleUser.getRoles();
        if (roles == null) {
            LOGGER.warn("msg=roles were null, user={}, userId={}", exampleUser.getName(), exampleUser.getId());
            return false;
        }
        return roles.contains(requiredRole);
    }
}
