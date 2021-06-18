package org.core.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.core.auth.Secrets;
import org.core.auth.jwt.UserRoles;
import org.core.db.daos.UserDAO;
import org.core.response.LoginResponse;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;


import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.jersey.caching.CacheControl;


@Path("auth")
@Produces(APPLICATION_JSON)
public class LoginResource {

    private UserDAO userDao;

    public LoginResource(UserDAO userDao){
        this.userDao = userDao;
    }
    @GET
    @Path("/login")
    @CacheControl(noCache = true, noStore = true, mustRevalidate = true, maxAge = 0)
    public final LoginResponse doLogin(@Auth PrincipalImpl user) throws JoseException {

        return new LoginResponse(buildToken(user).getCompactSerialization());
    }

//    @POST
//    @Path("/login")
//    @CacheControl(noCache = true, noStore = true, mustRevalidate = true, maxAge = 0)
//    public final LoginResponse doLogin(@NotNull final LoginUser loginUser, @Auth PrincipalImpl user) throws JoseException {
//        System.out.println(loginUser.getUsername()+"  "+loginUser.getPassword());
//        return new LoginResponse(buildToken(user).getCompactSerialization());
//    }


    private JsonWebSignature buildToken(PrincipalImpl user) {
        // These claims would be tightened up for production
        final JwtClaims claims = new JwtClaims();
        claims.setSubject("1");
        claims.setStringClaim("roles", userDao.getRoleByUserName(user.getName()));
        claims.setStringClaim("user", user.getName());
        claims.setIssuedAtToNow();
        claims.setGeneratedJwtId();

        final JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(HMAC_SHA256);
        jws.setKey(new HmacKey(Secrets.JWT_SECRET_KEY));
        return jws;
    }
}
