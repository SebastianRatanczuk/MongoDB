package com.example.mongo.user;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final MongoTemplate mongoTemplate;
    private static final String COLLECTION_NAME = "user";

    public void save(Document object) {
        mongoTemplate.insert(object, COLLECTION_NAME);
    }

    public List<Document> find() {
        return mongoTemplate.findAll(Document.class, COLLECTION_NAME);
    }

    public void update(String id, Update update) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
    }

    public Document findById(String id) {
        return mongoTemplate.findById(id, Document.class, COLLECTION_NAME);
    }

    public void deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Document.class, COLLECTION_NAME);
    }
}
