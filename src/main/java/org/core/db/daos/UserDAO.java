package org.core.db.daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.core.api.Donut;
import org.core.api.User;
import org.core.util.DonutMapper;
import org.core.util.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {
    final MongoCollection<Document> userCollection;

    public UserDAO(final MongoCollection<Document> userCollection) {
        this.userCollection = userCollection;
    }
    public List<User> getAll() {
        final MongoCursor<Document> users = userCollection.find().iterator();
        final List<User> usersFind = new ArrayList<>();
        try {
            while (users.hasNext()) {
                final Document user = users.next();
                usersFind.add(UserMapper.map(user));
            }
        } finally {
            users.close();
        }
        return usersFind;
    }

    public User getOne(final ObjectId id) {
        final Optional<Document> userFind = Optional.ofNullable(userCollection.find(new Document("_id", id)).first());
        return userFind.isPresent() ? UserMapper.map(userFind.get()) : null;
    }

    public void save(final User user){
        final Document saveUser = new Document("username",user.getUsername()).append
                ("password",user.getPassword()).append
                ("email",user.getEmail()).append
                ("mobile",user.getMobile());
        userCollection.insertOne(saveUser);
    }

    public void update(final ObjectId id, final User user) {
        userCollection.updateOne(new Document("_id", id),
                new Document("$set", new Document("username",user.getUsername()).append
                        ("password",user.getPassword()).append
                        ("email",user.getEmail()).append
                        ("mobile",user.getMobile()))
        );
    }
    public void delete(final ObjectId id){
        userCollection.deleteOne(new Document("_id", id));
    }
}