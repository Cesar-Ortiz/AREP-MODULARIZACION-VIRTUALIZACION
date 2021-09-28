package edu.escuelaing.arep.dockerdemo;

import edu.escuelaing.arep.dockerdemo.conection.MongoConection;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

import java.util.ArrayList;

import static spark.Spark.*;

public class SparkWebServer {

    static MongoConection mongoConection;
    public static void main(String... args){
        mongoConection=MongoConection.getInstance();
        port(getPort());
        staticFiles.location("/public");
        get("hell", (req,res) -> "Hello Docker!");
        get("amazon/:entrada", (req,res) -> setText(req, res));
        post("amazon/resultado", (req,res) -> getText(req, res));
    }

    private static String setText(Request req, Response res) {
        Table t = new Table(req.params(":entrada"));
        mongoConection.save(t);
        String JSON=getText(req,res);
        return JSON;
    }

    private static String getText(Request req, Response res) {
        res.type("application/json");
        ArrayList<Table> tabla=mongoConection.getText();
        ArrayList<Table> t=new ArrayList<Table>();
        for(int i=tabla.size()-10;i<tabla.size();i++) {
            if(i>=0) {
                t.add(tabla.get(i));
            }
        }
        Gson gson=new Gson();
        String JSON= gson.toJson(t);
        return JSON;
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
