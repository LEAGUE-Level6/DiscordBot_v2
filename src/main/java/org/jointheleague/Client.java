package org.jointheleague;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

public class Client {
    static MongoClient client = MongoClients.create(System.getenv("MONGO_URI"));

    public static MongoDatabase database = client.getDatabase("MincoPenguinDB");

    public static MongoCollection<Document> collection = database.getCollection("profilemodels");

    public static Document findOne(String id) {
        return collection.find(Filters.eq("userID", id)).first();
    }
    public static void findOneAndUpdate(String id, Bson newData) {
        collection.updateOne(Filters.eq("userID", id), newData);
    }
}
