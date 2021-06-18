package org.core.db.daos;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;

import org.core.api.User;
import org.core.util.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.eq;

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
                usersFind.add(UserMapper.map(user,0));
            }
        } finally {
            users.close();
        }
        return usersFind;
    }

    public User getOne(final ObjectId id) {
        final Optional<Document> userFind = Optional.ofNullable(userCollection.find(new Document("_id", id)).first());
        return userFind.isPresent() ? UserMapper.map(userFind.get(),0) : null;
    }
    public List<User> getUserByEmail(String email,int isPassRequired) {
        List<User> users = new ArrayList<>();
        FindIterable<Document> courtDocuments = userCollection.find(
                eq("email", email));
        List<Document> docs = StreamSupport.stream(courtDocuments.spliterator(), false).collect(Collectors.toList());
        for(Document document : docs) {
            users.add(UserMapper.map(document,0));
        }
        return users;
    }

    public String getRoleByUserName(String username) {
        return getUserByUserName(username,0).get(0).getRole();
    }
    public List<User> getUserByUserName(String username,int isPassRequired) {
        List<User> users = new ArrayList<>();
        FindIterable<Document> courtDocuments = userCollection.find(
                eq("username", username));
        List<Document> docs = StreamSupport.stream(courtDocuments.spliterator(), false).collect(Collectors.toList());
        for(Document document : docs) {
            users.add(UserMapper.map(document,isPassRequired));
        }
        return users;
    }
    public int save(final User user){
        if(getUserByEmail(user.getEmail(),0).size() > 0){
            return 1;
        }
        if(getUserByUserName(user.getUsername(),0).size() > 0){
            return 2;
        }
        final Document saveUser = new Document("username",user.getUsername()).append
                ("password",user.getPassword()).append
                ("email",user.getEmail()).append
                ("firstName",user.getFirstName()).append
                ("lastName",user.getLastName()).append
                ("role",user.getRole()).append
                ("mobile",user.getMobile());
        userCollection.insertOne(saveUser);
        return 0;
    }

    public void update(final ObjectId id, final User user) {
        userCollection.updateOne(new Document("_id", id),
                new Document("$set", new Document("username",user.getUsername()).append
                        ("password",user.getPassword()).append
                        ("email",user.getEmail()).append
                        ("firstName",user.getFirstName()).append
                        ("lastName",user.getLastName()).append
                        ("role",user.getRole()).append
                        ("mobile",user.getMobile()))
        );
    }
    public void delete(final ObjectId id){
        userCollection.deleteOne(new Document("_id", id));
    }
}