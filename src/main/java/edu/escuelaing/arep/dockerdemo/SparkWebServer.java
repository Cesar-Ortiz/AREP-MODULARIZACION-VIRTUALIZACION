package edu.escuelaing.arep.dockerdemo;

import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class SparkWebServer {

    public static void main(String... args){
        port(getPort());
        staticFiles.location("/public");
        get("hello", (req,res) -> "Hello Docker!");
        get("amazon/:entrada", (req,res) -> "Hello Docker!");
    }

    private static void getXData(Request req, Response res) {
        //res.type("application/json");
        String entrada = req.params(":symbol");
    }

        private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
