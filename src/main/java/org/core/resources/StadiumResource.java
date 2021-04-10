package org.core.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bson.types.ObjectId;
import org.core.api.Stadium;
import org.core.db.daos.StadiumDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "stadium",
        description = "Stadium Resource",
        tags = {"Stadiums"})
@Path("/stadium")
@Produces(MediaType.APPLICATION_JSON)
public class StadiumResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StadiumResource.class);

    private StadiumDAO stadiumDAO;

    public StadiumResource(final StadiumDAO stadiumDAO) {
        this.stadiumDAO = stadiumDAO;
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "Stadium not found")
    })
    @GET
    public Response all() {
        LOGGER.info("List all Stadium.");
        final List<Stadium> stadiumsFind = stadiumDAO.getAll();
        if (stadiumsFind.isEmpty()) {
            return Response.accepted(new org.core.api.Response("Stadium not found."))
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(stadiumsFind).build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "Stadiums not found")
    })
    @GET
    @Path("/{id}")
    public Response getOne(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Find the Stadium by identifier : " + id.toString());
        final Stadium stadium = stadiumDAO.getOne(id);
        if (stadium != null) {
            return Response.ok(stadium).build();
        }
        return Response.accepted(new org.core.api.Response("Stadium not found.")).build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@ApiParam(value = "Stadium") @NotNull final Stadium stadium) {
        LOGGER.info("Persist a stadium in collection with the information: {}", stadium);
        stadiumDAO.save(stadium);
        return Response.status(Response.Status.CREATED).build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @PUT
    @Path("/{id}")
    public Response update(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id,
                           @ApiParam(value = "Stadium") @NotNull final Stadium stadium) {
        LOGGER.info("Update the information of a stadium : {} ", stadium);
        stadiumDAO.update(id, stadium);
        return Response.ok().build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Delete a stadium from collection with identifier: " + id.toString());
        stadiumDAO.delete(id);
        return Response.ok().build();
    }
}
