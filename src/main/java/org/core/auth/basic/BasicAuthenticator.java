package org.core.auth.basic;

import java.util.Optional;


import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentials;
import org.core.auth.Secrets;

public class BasicAuthenticator implements Authenticator<BasicCredentials, PrincipalImpl> {

    @Override
    public Optional<PrincipalImpl> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (isValidCredentials(credentials)) {
            return Optional.of(new PrincipalImpl(credentials.getUsername()));
        }
        return Optional.empty();
    }

    private boolean isValidCredentials(BasicCredentials credentials) {
        return Secrets.LOGIN_USERNAME.equals(credentials.getUsername()) && (Secrets.LOGIN_PASSWORD.equals(credentials.getPassword()));
    }
}