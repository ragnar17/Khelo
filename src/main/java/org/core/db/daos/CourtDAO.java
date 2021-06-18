package org.core.db.daos;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.core.api.Booking;
import org.core.api.Court;
import org.core.api.Court;
import org.core.util.BookingMapper;
import org.core.util.CourtMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.eq;

public class CourtDAO {
    final MongoCollection<Document> courtCollection;

    public CourtDAO(final MongoCollection<Document> courtCollection) {
        this.courtCollection = courtCollection;
    }
    public List<Court> getAll() {
        final MongoCursor<Document> courts = courtCollection.find().iterator();
        final List<Court> courtsFind = new ArrayList<>();
        try {
            while (courts.hasNext()) {
                final Document court = courts.next();
                courtsFind.add(CourtMapper.map(court));
            }
        } finally {
            courts.close();
        }
        return courtsFind;
    }

    public Court getOne(final ObjectId id) {
        final Optional<Document> courtFind = Optional.ofNullable(courtCollection.find(new Document("_id", id)).first());
        return courtFind.isPresent() ? CourtMapper.map(courtFind.get()) : null;
    }

    public List<Court> getCourtsByStadiumId(String stadium_id) {
        List<Court> courts = new ArrayList<>();
        FindIterable<Document> courtDocuments = courtCollection.find(
                eq("stadium_id", stadium_id));
        List<Document> docs = StreamSupport.stream(courtDocuments.spliterator(), false).collect(Collectors.toList());
        for(Document document : docs) {
            courts.add(CourtMapper.map(document));
        }
        return courts;
    }

    public void save(final Court court){
        final Document saveCourt = new Document("type",court.getType()).append
                ("stadium_id",court.getStadium_id()).append
                ("court_number",court.getCourt_number());
        courtCollection.insertOne(saveCourt);
    }

    public void update(final ObjectId id, final Court court) {
        courtCollection.updateOne(new Document("_id", id),
                new Document("type",court.getType()).append
                        ("stadium_id",court.getStadium_id()).append
                        ("court_number",court.getCourt_number())
        );
    }
    public void delete(final ObjectId id){
        courtCollection.deleteOne(new Document("_id", id));
    }
}
