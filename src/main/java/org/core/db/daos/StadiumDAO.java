package org.core.db.daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.core.api.Stadium;
import org.core.api.User;
import org.core.util.StadiumMapper;
import org.core.util.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class  StadiumDAO {
    final MongoCollection<Document> stadiumCollection;

    public StadiumDAO(final MongoCollection<Document> stadiumCollection) {
        this.stadiumCollection = stadiumCollection;
    }
    public List<Stadium> getAll() {
        final MongoCursor<Document> stadiums = stadiumCollection.find().iterator();
        final List<Stadium> stadiumsFind = new ArrayList<>();
        try {
            while (stadiums.hasNext()) {
                final Document stadium = stadiums.next();
                stadiumsFind.add(StadiumMapper.map(stadium));
            }
        } finally {
            stadiums.close();
        }
        return stadiumsFind;
    }

    public Stadium getOne(final ObjectId id) {
        final Optional<Document> stadiumFind = Optional.ofNullable(stadiumCollection.find(new Document("_id", id)).first());
        return stadiumFind.isPresent() ? StadiumMapper.map(stadiumFind.get()) : null;
    }

    public void save(final Stadium stadium){
        final Document saveStadium = new Document("sellerid",stadium.getSellerid()).append
                ("address",stadium.getAddress()).append
                ("name",stadium.getName());
        stadiumCollection.insertOne(saveStadium);
    }

    public void update(final ObjectId id, final Stadium stadium) {
        stadiumCollection.updateOne(new Document("_id", id),
                new Document("sellerid",stadium.getSellerid()).append
                        ("address",stadium.getAddress()).append
                        ("name",stadium.getName())
        );
    }
    public void delete(final ObjectId id){
        stadiumCollection.deleteOne(new Document("_id", id));
    }
}
