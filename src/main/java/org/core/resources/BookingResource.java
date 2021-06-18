package org.core.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bson.types.ObjectId;


import org.core.api.Booking;
import org.core.db.daos.BookingDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "booking",
        description = "Booking Resource",
        tags = {"Bookings"})
@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingResource.class);

    private BookingDAO bookingDAO;

    public BookingResource(final BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "Booking not found")
    })
    @GET
    public Response all() {
        LOGGER.info("List all Booking.");
        final List<Booking> bookingsFind = bookingDAO.getAll();
        if (bookingsFind.isEmpty()) {
            return Response.accepted(new org.core.api.Response("booking not found."))
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(bookingsFind).build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "bookings not found")
    })
    @GET
    @Path("/{id}")
    public Response getOne(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Find the Stadium by identifier : " + id.toString());
        final Booking booking = bookingDAO.getOne(id);
        if (booking != null) {
            return Response.ok(booking).build();
        }
        return Response.accepted(new org.core.api.Response("Booking not found.")).build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@ApiParam(value = "Booking") @NotNull final Booking booking) {
        LOGGER.info("Persist a booking in collection with the information: {}", booking);
        bookingDAO.save(booking);
        return Response.status(Response.Status.CREATED).build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @PUT
    @Path("/{id}")
    public Response update(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id,
                           @ApiParam(value = "Booking") @NotNull final Booking booking) {
        LOGGER.info("Update the information of a booking : {} ", booking);
        bookingDAO.update(id, booking);
        return Response.ok().build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Delete a booking from collection with identifier: " + id.toString());
        bookingDAO.delete(id);
        return Response.ok().build();
    }
}