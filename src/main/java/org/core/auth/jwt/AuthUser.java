package org.core.auth.jwt;

import java.security.Principal;

public class AuthUser implements Principal {

    private final long id;
    private final String name;
    private final String roles;

    public AuthUser(long id, String name, String roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthUser)) {
            return false;
        }

        AuthUser authUser = (AuthUser) o;

        if (id != authUser.id) {
            return false;
        }
        if (name != null ? !name.equals(authUser.name) : authUser.name != null) {
            return false;
        }
        return roles != null ? roles.equals(authUser.roles) : authUser.roles == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }
}