package cs_340.communicators;

import com.google.gson.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import cs_340.common.Command;

/**
 * Created by Pultilian on 2/1/2018.
 */
public class ClientCommunicator {
    public String encode(Command request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Command decode(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Command.class);
    }

    public Command send(String urlSuffix, Command requestInfo) {
        try {
            // Establishes the URL and connects to the server.
            URL url = new URL("http://127.0.0.1:8081" + urlSuffix);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.addRequestProperty("Accept", "application/json");
                urlConnection.connect();
                String sendRequest = encode(requestInfo);

                OutputStream requestBody = urlConnection.getOutputStream();
                writeString(sendRequest, requestBody);
                requestBody.close();

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) { // Connection is successful
                    InputStream respBody = urlConnection.getInputStream();
                    String respData = readString(respBody);
                    Command result = JSONToObject(respData);
                    urlConnection.disconnect();
                    return result;
                } else { // Connection Fails
//                    System.out.println("ERROR: " + urlConnection.getResponseMessage());
                    urlConnection.disconnect();
                    return null;
                }
            } catch (Exception e) {
//                System.out.println("ERROR: " + e);
            }
        } catch (Exception e) {
//            System.out.println("ERROR: " + e);
        }
        return null;
    }

    private void writeString(String request, OutputStream body) {
        return;
    }

    private String readString(InputStream response) {
        return "";
    }

    private Command JSONToObject(String json) {
        return null;
    }
}
