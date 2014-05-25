package org.javers.repository.mongo.model;

import com.mongodb.BasicDBObject;

public class MongoHeadId extends BasicDBObject {

    public static final String COLLECTION_NAME = "HeadId";
    public static final String KEY = "id";

    public MongoHeadId(String headId) {
        super(KEY, headId);
    }
}
