package org.core.util;

import org.bson.Document;
import org.core.api.Booking;

import java.sql.Timestamp;

public class BookingMapper {
    public static Booking map(final Document bookingDocument) {
        final Booking booking = new Booking();
        booking.setId(bookingDocument.getObjectId("_id"));
        booking.setCourt_id(bookingDocument.getString("court_id"));
        booking.setUid(bookingDocument.getString("uid"));
        booking.setCreated_time(Timestamp.valueOf(bookingDocument.getString("created_time")));
        booking.setBooking_time(Timestamp.valueOf(bookingDocument.getString("booking_time")));
        booking.setDuration(bookingDocument.getInteger("duration"));
        return booking;
    }
}
