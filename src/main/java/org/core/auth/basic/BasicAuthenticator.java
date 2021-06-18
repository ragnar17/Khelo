package org.core.auth.basic;

import java.util.List;
import java.util.Optional;


import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentials;
import org.core.api.User;
import org.core.auth.Secrets;
import org.core.db.daos.UserDAO;

public class BasicAuthenticator implements Authenticator<BasicCredentials, PrincipalImpl> {

    private UserDAO userDAO ;

    public BasicAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<PrincipalImpl> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (isValidCredentials(credentials)) {
            return Optional.of(new PrincipalImpl(credentials.getUsername()));
        }
        return Optional.empty();
    }

    private boolean isValidCredentials(BasicCredentials credentials) {
        List<User> user = userDAO.getUserByUserName(credentials.getUsername(),1);
        if(user.size()==0){
            return false;
        }
        return user.get(0).getUsername().equals(credentials.getUsername()) && user.get(0).getPassword().equals(credentials.getPassword());
//        return Secrets.LOGIN_USERNAME.equals(credentials.getUsername()) && (Secrets.LOGIN_PASSWORD.equals(credentials.getPassword()));
    }
}