package org.core.auth.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.auth.Authorizer;


public class JwtAuthoriser implements Authorizer<AuthUser> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthoriser.class);

    @Override
    public boolean authorize(AuthUser authUser, String requiredRole) {
        if (authUser == null) {
            LOGGER.warn("msg=user object was null");
            return false;
        }

        String roles = authUser.getRoles();
        if (roles == null) {
            LOGGER.warn("msg=roles were null, user={}, userId={}", authUser.getName(), authUser.getId());
            return false;
        }
        return roles.contains(requiredRole);
    }
}
