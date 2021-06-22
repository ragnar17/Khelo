package org.core.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bson.types.ObjectId;
import org.core.api.User;
import org.core.db.daos.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "user",
        description = "User Resource",
        tags = {"Users"})
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private UserDAO userDAO;

    public UserResource(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GET
    public Response all() {
        LOGGER.info("List all Users.");
        final List<User> usersFind = userDAO.getAll();
        if (usersFind.isEmpty()) {
            return Response.accepted(new org.core.api.Response("Users not found."))
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(usersFind).build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "Users not found")
    })
    @GET
    @Path("/{id}")
    public Response getOne(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Find the User by identifier : " + id.toString());
        final User user = userDAO.getOne(id);
        if (user != null) {
            return Response.ok(user).build();
        }
        return Response.accepted(new org.core.api.Response("User not found.")).build();
    }


    @GET
    @Path("/username/{username}")
    public Response getUserDetails(@NotNull @PathParam("username") final String username) {
        LOGGER.info("Find the User by username : " + username);
        final List<User> users = userDAO.getUserByUserName(username,0);
        if (users.size() > 0) {
            return Response.ok(users.get(0)).build();
        }
        return Response.accepted(new org.core.api.Response("User not found.")).build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@ApiParam(value = "User") @NotNull final User user) {
        LOGGER.info("Persist a user in collection with the information: {}", user);
        int res = userDAO.save(user);
        if(res == 1) {
            return Response.status(Response.Status.CONFLICT).type(MediaType.APPLICATION_JSON).entity("An account already associated with this email.").build();
        }
        else if( res == 2){
            return Response.status(Response.Status.CONFLICT).type(MediaType.APPLICATION_JSON).entity("Username already exists.").build();
        }
        else{
            return Response.status(Response.Status.CREATED).type(MediaType.APPLICATION_JSON).entity("Account Created").build();
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @PUT
    @Path("/{id}")
    public Response update(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id,
                           @ApiParam(value = "User") @NotNull final User user) {
        LOGGER.info("Update the information of a user : {} ", user);
        userDAO.update(id, user);
        return Response.ok().build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Delete a user from collection with identifier: " + id.toString());
        userDAO.delete(id);
        return Response.ok().build();
    }
}