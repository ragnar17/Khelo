package org.core.db.daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.core.api.Seller;
import org.core.api.User;
import org.core.util.SellerMapper;
import org.core.util.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SellerDAO {
    final MongoCollection<Document> sellerCollection;

    public SellerDAO(final MongoCollection<Document> sellerCollection) {
        this.sellerCollection = sellerCollection;
    }
    public List<Seller> getAll() {
        final MongoCursor<Document> sellers = sellerCollection.find().iterator();
        final List<Seller> sellersFind = new ArrayList<>();
        try {
            while (sellers.hasNext()) {
                final Document seller = sellers.next();
                sellersFind.add(SellerMapper.map(seller));
            }
        } finally {
            sellers.close();
        }
        return sellersFind;
    }

    public Seller getOne(final ObjectId id) {
        final Optional<Document> sellerFind = Optional.ofNullable(sellerCollection.find(new Document("_id", id)).first());
        return sellerFind.isPresent() ? SellerMapper.map(sellerFind.get()) : null;
    }

    public void save(final Seller seller){
        final Document saveSeller = new Document("uid",seller.getUid()).append
                ("aadhar",seller.getAadhar()).append
                ("pan",seller.getPan()).append
                ("address",seller.getAddress()).append
                ("gstin",seller.getGstin()).append
//                ("docs",seller.getGstin()).append
                ("gstin",seller.getGstin()).append
                ("uid",seller.getUid());
        sellerCollection.insertOne(saveSeller);
    }

    public void update(final ObjectId id, final Seller seller) {
        sellerCollection.updateOne(new Document("_id", id),
                new Document("uid",seller.getUid()).append
                        ("aadhar",seller.getAadhar()).append
                        ("pan",seller.getPan()).append
                        ("address",seller.getAddress()).append
                        ("gstin",seller.getGstin()).append
//                        ("docs",seller.getGstin()).append
                        ("gstin",seller.getGstin()).append
                        ("uid",seller.getUid())
        );
    }
    public void delete(final ObjectId id){
        sellerCollection.deleteOne(new Document("_id", id));
    }
}
