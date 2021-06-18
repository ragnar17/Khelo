package org.core.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bson.types.ObjectId;


import org.core.api.Booking;
import org.core.api.BookingFindQuery;
import org.core.api.Court;
import org.core.api.Stadium;
import org.core.db.daos.BookingDAO;
import org.core.db.daos.CourtDAO;
import org.core.db.daos.StadiumDAO;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Api(value = "bookinghandler",
        description = "Booking Handler Resource",
        tags = {"Bookinghandler"})
@Path("/booking-handler")
@Produces(MediaType.APPLICATION_JSON)
public class BookingHandlerResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingHandlerResource.class);

    private BookingDAO bookingDAO;
    private CourtDAO courtDAO;
    private StadiumDAO stadiumDAO;
    public BookingHandlerResource(final BookingDAO bookingDAO,final CourtDAO courtDAO,final StadiumDAO stadiumDAO) {
        this.bookingDAO = bookingDAO;
        this.courtDAO = courtDAO;
        this.stadiumDAO = stadiumDAO;
    }

    @Path("/find")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findStadiums(final BookingFindQuery bookingFindQuery) {
        Time start = bookingFindQuery.getTime();
        Time end = Time.valueOf(start.toLocalTime().plusMinutes(bookingFindQuery.getDuration()));
        List<Stadium> stadiums = stadiumDAO.getAll();
        for(Stadium stadium : stadiums) {
            List<Court> courts = courtDAO.getCourtsByStadiumId(stadium.getId().toString());
            List<Court> available_courts = new ArrayList<>();
            for(Court court : courts) {
                List<Booking> bookings = bookingDAO.getBookingsByCourtId(court.getId().toString());
                boolean check = true;
                for(Booking booking : bookings){
                    Time booking_start = Time.valueOf(new SimpleDateFormat("HH:mm:ss").format(booking.getBooking_time()));
                    Time booking_end = Time.valueOf(booking_start.toLocalTime().plusMinutes(booking.getDuration()));

                    Time maxStart = start;
                    if(start.compareTo(booking_end) == 1){
                        maxStart = booking_start;
                    }
                    Time minEnd = end;
                    if(booking_end.compareTo(end) == 1){
                        minEnd = end;
                    }
                    if(maxStart.compareTo(minEnd) != 1){
                        check = false;
                        break;
                    }
//                    System.out.println(start + " " + end);
//                    System.out.println(booking_start + " " + booking_end);
//                    System.out.println(maxStart + " " + minEnd);
//                    System.out.println(maxStart.compareTo(minEnd));
                }
                if(check) {
                    available_courts.add(court);
                }
            }
        }

        return Response.status(Response.Status.CREATED).build();
    }

}