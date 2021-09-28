package edu.escuelaing.arep.dockerdemo.conection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.escuelaing.arep.dockerdemo.Table;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MongoConection{
    public static final MongoConection _instance=new MongoConection();

    public static MongoConection getInstance() {
        return _instance;
    }

    public static void save(Table table) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://db")) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("docker");
            MongoCollection<Document> dataCollection = sampleTrainingDB.getCollection("data");

            Document info = new Document("_id", new ObjectId());
            info.append("text", table.getText())
                    .append("date", table.getDate());
            dataCollection.insertOne(info);
        }
    }

    public static ArrayList<Table> getText(){
        try (MongoClient mongoClient = MongoClients.create("mongodb://db")) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("docker");
            MongoCollection<Document> dataCollection = sampleTrainingDB.getCollection("data");

            ArrayList<Table> datos=new ArrayList<Table>();
            for(Document d:dataCollection.find()) {
                datos.add(new Table((String)d.get("text"),(Date)d.get("date")));
            }
            // find one document with new Document

            return datos;
        }
    }
}

