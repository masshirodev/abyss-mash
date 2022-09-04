package modules;

import abyss.plugin.api.world.WorldTile;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import enums.LocationType;
import models.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class DatabaseHandler {
    private static final String databaseUrl = "mongodb+srv://masshirodev:jpkQ8M12@mashdb.z7pa05o.mongodb.net/?retryWrites=true&w=majority";
    private static MongoClient GetClient() {
        return MongoClients.create(databaseUrl);
    }

//    public static LocationModel GetLocation() {
//        try (MongoClient mongoClient = GetClient()) {
//            MongoDatabase database = mongoClient.getDatabase("mash");
//            MongoCollection<Document> collection = database.getCollection("locations");
//
//            Bson projectionFields = Projections.fields(
//                    Projections.include("title", "imdb"),
//                    Projections.excludeId());
//
//            Document doc = collection.find(eq("title", "The Room"))
//                    .projection(projectionFields)
//                    .sort(Sorts.descending("imdb.rating"))
//                    .first();
//
//            if (doc == null) {
//                return LocationModel.MapFromDocument(doc);
//            }
//        }
//
//        return null;
//    }
}
