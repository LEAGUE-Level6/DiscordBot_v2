package org.jointheleague;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;

public class Client {
    static MongoClient client = MongoClients.create(System.getenv("MONGO_URI"));

    public static MongoDatabase database = client.getDatabase("MincoPenguinDB");

    public static MongoCollection<Document> collection = database.getCollection("profilemodels");

    public static Document findOne(String id) {
        Document c = collection.find(Filters.eq("userID", id)).first();
        if (c == null) {
            Document document = new Document("userID", id).append("mincoDollars", 50).append("bank", 0).append("inventory", new ArrayList<String>());
            collection.insertOne(document);
            return document;
        }
        return c;
    }

    public static void findOneAndUpdate(String id, Bson newData) {
        if (collection.find(Filters.eq("userID", id)).first() == null) {
            Document document = new Document("userID", id).append("mincoDollars", 50).append("bank", 0).append("inventory", new ArrayList<String>());
            collection.insertOne(document);
        }
        collection.updateOne(Filters.eq("userID", id), newData);
    }
}
