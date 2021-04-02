package org.core.health;

import org.bson.Document;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.client.MongoClient;

public class DropwizardMongoDBHealthCheck extends HealthCheck {

    /**
     * A client of MongoDB.
     */
    private MongoClient mongoClient;

    /**
     * Constructor.
     *
     * @param mongoClient the mongo client.
     */
    public DropwizardMongoDBHealthCheck(final MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() {
        try {
            final Document document = mongoClient.getDatabase("donuts").runCommand(new Document("buildInfo", 1));
            if (document == null) {
                return Result.unhealthy("Can not perform operation buildInfo in Database.");
            }
        } catch (final Exception e) {
            return Result.unhealthy("Can not get the information from database.");
        }
        return Result.healthy();
    }
}