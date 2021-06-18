package org.core.response;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import io.dropwizard.auth.Auth;
import org.core.auth.jwt.AuthUser;
import org.core.auth.jwt.UserRoles;

@Path("/protectedResourceTwo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProtectedResourceTwo {

    @GET
    @RolesAllowed({ UserRoles.ROLE_SELLER })
    public ProtectedResourceResponse getAuthProcessingTime(@Auth AuthUser user) throws Exception {

        return new ProtectedResourceResponse(user.getRoles(), user.getName());

    }
}
