package edu.escuelaing.arep.dockerdemo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpService {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final HttpService instance= new HttpService();
    public static HttpService getInstance() {
        return instance;
    }
    public String getXData(String url) throws IOException {
        System.out.println("------------------getXData------------------");
        String responseStr = "None";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            responseStr = response.toString();
            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return responseStr;
    }

    public String postXData(String url2,String entrada) throws IOException {
        String responseStr=null;

        URL url = new URL(url2);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("POST");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write(entrada);
        out.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                httpCon.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        responseStr = response.toString();
        return responseStr;

    }
    /*public String postXData(String url, String entrada) throws IOException{
        String responseStr = "None";
        URL obj = new URL (url+entrada);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "{"name": "Upendra", "job": "Programmer"}";
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            responseStr= response.toString();
            System.out.println(responseStr);
        }
        return responseStr;
    }*/
}
