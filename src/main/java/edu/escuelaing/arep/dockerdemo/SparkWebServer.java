package edu.escuelaing.arep.dockerdemo;

import edu.escuelaing.arep.dockerdemo.conection.MongoConection;
import spark.Filter;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import static spark.Spark.*;

public class SparkWebServer {
    private static RoundRobin wr;
    private static HttpService servicio;
    static MongoConection mongoConection;
    public static void main(String... args){
        wr=RoundRobin.getInstance();
        servicio=HttpService.getInstance();
        mongoConection=MongoConection.getInstance();
        port(getPort());
        staticFiles.location("/public");
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin","*");
            response.header("Access-Control-Allow-Methods","GET");
        });
        get("hell", (req,res) -> "Hello Docker!");
        post("amazondocker/entra", (req,res) -> enviarPalabra(req, res));
        get("amazondocker/resultado", (req,res) -> obtenerDatos(req, res));
        post("amazon/entra", (req,res) -> setText(req, res));
        get("amazon/resultado", (req,res) -> getText(req, res));
    }

    private static String enviarPalabra(Request req, Response res) {
        System.out.println(req.body()+" requeeeeeeeeeest");
        String urlService=wr.getRoundRobin();
        System.out.println(urlService);
        String resultado="";
        try {
            resultado=servicio.postXData(urlService+"amazon/entra", req.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        res.type("application/json");
        return resultado;
    }

    private static String obtenerDatos(Request req, Response res) {
        String urlService=wr.getRoundRobin();
        System.out.println(urlService+"----------------------url-------");
        String resultado="";
        try {
            resultado=servicio.getXData(urlService+"amazon/resultado");
            System.out.println(resultado+"----------------------salio-------");
        } catch (IOException e) {
            e.printStackTrace();
        }
        res.type("application/json");

        return resultado;
    }

    private static String setText(Request req, Response res) {
        System.out.println(req.body()+" requeeeeeeeeeest");
        Table t = new Table(req.body());
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
