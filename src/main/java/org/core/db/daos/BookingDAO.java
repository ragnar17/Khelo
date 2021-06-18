package org.core.db.daos;

import com.codahale.metrics.annotation.Timed;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.core.api.Booking;
import org.core.util.BookingMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static com.mongodb.client.model.Filters.*;

public class BookingDAO {
    final MongoCollection<Document> bookingCollection;

    public BookingDAO(final MongoCollection<Document> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }
    public List<Booking> getAll() {
        final MongoCursor<Document> bookings = bookingCollection.find().iterator();
        final List<Booking> bookingsFind = new ArrayList<>();
        try {
            while (bookings.hasNext()) {
                final Document booking = bookings.next();
                bookingsFind.add(BookingMapper.map(booking));
            }
        } finally {
            bookings.close();
        }
        return bookingsFind;
    }

    public Booking getOne(final ObjectId id) {
        final Optional<Document> bookingFind = Optional.ofNullable(bookingCollection.find(new Document("_id", id)).first());
        return bookingFind.isPresent() ? BookingMapper.map(bookingFind.get()) : null;
    }

    public void save(final Booking booking){
        final Document saveBooking = new Document("uid",booking.getUid()).append
                ("court_id",booking.getCourt_id()).append
                ("created_time",booking.getCreated_time().toString()).append
                ("booking_time",booking.getBooking_time().toString()).append
                ("duration",booking.getDuration());
        bookingCollection.insertOne(saveBooking);
    }

    public void update(final ObjectId id, final Booking booking) {
        bookingCollection.updateOne(new Document("_id", id),
                new Document("uid",booking.getUid()).append
                        ("court_id",booking.getCourt_id()).append
                        ("created_time",booking.getCreated_time().toString()).append
                        ("booking_time",booking.getBooking_time().toString()).append
                        ("duration",booking.getDuration())
        );
    }
    public void delete(final ObjectId id){
        bookingCollection.deleteOne(new Document("_id", id));
    }

    public List<Booking> getBookingsByCourtId(String court_id) {
        List<Booking> bookings = new ArrayList<>();
        FindIterable<Document> bookingDocuments = bookingCollection.find(
                eq("court_id", court_id));
        List<Document> docs = StreamSupport.stream(bookingDocuments.spliterator(), false).collect(Collectors.toList());
        for(Document document : docs)
        {
            bookings.add(BookingMapper.map(document));
        }
        return bookings;
    }
}
