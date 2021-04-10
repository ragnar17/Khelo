package org.core.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bson.types.ObjectId;

import org.core.api.Seller;
import org.core.db.daos.SellerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "seller",
        description = "Seller Resource",
        tags = {"Sellers"})
@Path("/seller")
@Produces(MediaType.APPLICATION_JSON)
public class SellerResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerResource.class);

    private SellerDAO sellerDAO;

    public SellerResource(final SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "Seller not found")
    })
    @GET
    public Response all() {
        LOGGER.info("List all Seller.");
        final List<Seller> sellersFind = sellerDAO.getAll();
        if (sellersFind.isEmpty()) {
            return Response.accepted(new org.core.api.Response("seller not found."))
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(sellersFind).build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "sellers not found")
    })
    @GET
    @Path("/{id}")
    public Response getOne(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Find the Stadium by identifier : " + id.toString());
        final Seller seller = sellerDAO.getOne(id);
        if (seller != null) {
            return Response.ok(seller).build();
        }
        return Response.accepted(new org.core.api.Response("Seller not found.")).build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@ApiParam(value = "Seller") @NotNull final Seller seller) {
        LOGGER.info("Persist a seller in collection with the information: {}", seller);
        sellerDAO.save(seller);
        return Response.status(Response.Status.CREATED).build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @PUT
    @Path("/{id}")
    public Response update(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id,
                           @ApiParam(value = "Seller") @NotNull final Seller seller) {
        LOGGER.info("Update the information of a seller : {} ", seller);
        sellerDAO.update(id, seller);
        return Response.ok().build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Delete a seller from collection with identifier: " + id.toString());
        sellerDAO.delete(id);
        return Response.ok().build();
    }
}
