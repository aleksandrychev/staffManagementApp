package com.example.igor.login.test.myapplication.clients;

import android.content.Context;

import com.example.igor.login.test.myapplication.heplers.PreferenceHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by igor on 26.10.16.
 */

public class HttpClient {
    private static String host = "http://staff.aleksandrychev.name/";
    // HTTP GET request
    public static void sendGet() throws Exception {

        URL obj = new URL(host);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");


//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
//        System.out.println(response.toString());

    }

    // HTTP POST request
    public static String sendPost(String urlParameters) throws Exception {

        String url = "http://staff.aleksandrychev.name/api/v1/auth/login";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");

        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Authorization", "Bearer 111111");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
//
//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + urlParameters);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
//        System.out.println(response.toString());
        //print result
        return response.toString();

    }

    // HTTP POST request
    public static String sendPut(String urlPart,String urlParameters, Context appContext) throws Exception {

        String url =  host + urlPart;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("PUT");

        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Authorization", "Bearer " +  PreferenceHelper.getDefaults("tokenKey", appContext));

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        //print result
        return response.toString();

    }

    // HTTP POST request
    public static String sendPostv1(String urlPart,String urlParameters, Context appContext) throws Exception {

        String url =  host + urlPart;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");

        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Authorization", "Bearer " +  PreferenceHelper.getDefaults("tokenKey", appContext));

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        //print result
        return response.toString();

    }

    // HTTP POST request
    public static String getTasks(int page, String status, Context appContext) throws Exception {

        String urlParameters = "?status="+status+"&page=" + page;
        String url = host + "api/v1/task" + urlParameters;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + PreferenceHelper.getDefaults("tokenKey", appContext));

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
//        System.out.println(response.toString());
        //print result
        return response.toString();

    }

    // HTTP POST request
    public static String getTask(Context appContext, String id) throws Exception {


        String url = host + "api/v1/task/" + id;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + PreferenceHelper.getDefaults("tokenKey", appContext));

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
//        System.out.println(response.toString());
        //print result
        return response.toString();

    }
}
