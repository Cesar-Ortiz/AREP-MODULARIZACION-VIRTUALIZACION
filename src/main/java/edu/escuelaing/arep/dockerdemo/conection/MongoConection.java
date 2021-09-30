package edu.escuelaing.arep.dockerdemo.conection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.escuelaing.arep.dockerdemo.Data;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

public class MongoConection{
    public static final MongoConection _instance=new MongoConection();

    public static MongoConection getInstance() {
        return _instance;
    }

    public static void save(Data data) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://db")) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("docker");
            MongoCollection<Document> dataCollection = sampleTrainingDB.getCollection("data");

            Document info = new Document("_id", new ObjectId());
            info.append("text", data.getText())
                    .append("date", data.getDate());
            dataCollection.insertOne(info);
        }
    }

    public static ArrayList<Data> getText(){
        try (MongoClient mongoClient = MongoClients.create("mongodb://db")) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("docker");
            MongoCollection<Document> dataCollection = sampleTrainingDB.getCollection("data");

            ArrayList<Data> datos=new ArrayList<Data>();
            for(Document d:dataCollection.find()) {
                datos.add(new Data((String)d.get("text"),(Date)d.get("date")));
            }
            // find one document with new Document

            return datos;
        }
    }
}

