package org.core.db.daos;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.core.util.DonutMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.core.api.Donut;

public class DonutDAO {
    final MongoCollection<Document> donutCollection;

    public DonutDAO(final MongoCollection<Document> donutCollection) {
        this.donutCollection = donutCollection;
    }
    public List<Donut> getAll() {
        final MongoCursor<Document> donuts = donutCollection.find().iterator();
        final List<Donut> donutsFind = new ArrayList<>();
        try {
            while (donuts.hasNext()) {
                final Document donut = donuts.next();
                donutsFind.add(DonutMapper.map(donut));
            }
        } finally {
            donuts.close();
        }
        return donutsFind;
    }

    public Donut getOne(final ObjectId id) {
        final Optional<Document> donutFind = Optional.ofNullable(donutCollection.find(new Document("_id", id)).first());
        return donutFind.isPresent() ? DonutMapper.map(donutFind.get()) : null;
    }

    public void save(final Donut donut){
        final Document saveDonut = new Document("price", donut.getPrice())
                .append("flavor", donut.getFlavor());
        donutCollection.insertOne(saveDonut);
    }

    public void update(final ObjectId id, final Donut donut) {
        donutCollection.updateOne(new Document("_id", id),
                new Document("$set", new Document("price", donut.getPrice())
                        .append("flavor", donut.getFlavor()))
        );
    }
    public void delete(final ObjectId id){
        donutCollection.deleteOne(new Document("_id", id));
    }
}
