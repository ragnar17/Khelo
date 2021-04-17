package org.core.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bson.types.ObjectId;
import org.core.api.Court;
import org.core.db.daos.CourtDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "court",
        description = "Court Resource",
        tags = {"Courts"})
@Path("/court")
@Produces(MediaType.APPLICATION_JSON)
public class CourtResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourtResource.class);

    private CourtDAO courtDAO;

    public CourtResource(final CourtDAO courtDAO) {
        this.courtDAO = courtDAO;
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "Court not found")
    })
    @GET
    public Response all() {
        LOGGER.info("List all Court.");
        final List<Court> courtsFind = courtDAO.getAll();
        if (courtsFind.isEmpty()) {
            return Response.accepted(new org.core.api.Response("court not found."))
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(courtsFind).build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "courts not found")
    })
    @GET
    @Path("/{id}")
    public Response getOne(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Find the Stadium by identifier : " + id.toString());
        final Court court = courtDAO.getOne(id);
        if (court != null) {
            return Response.ok(court).build();
        }
        return Response.accepted(new org.core.api.Response("Court not found.")).build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@ApiParam(value = "Court") @NotNull final Court court) {
        LOGGER.info("Persist a court in collection with the information: {}", court);
        courtDAO.save(court);
        return Response.status(Response.Status.CREATED).build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @PUT
    @Path("/{id}")
    public Response update(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id,
                           @ApiParam(value = "Court") @NotNull final Court court) {
        LOGGER.info("Update the information of a court : {} ", court);
        courtDAO.update(id, court);
        return Response.ok().build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Delete a court from collection with identifier: " + id.toString());
        courtDAO.delete(id);
        return Response.ok().build();
    }
}