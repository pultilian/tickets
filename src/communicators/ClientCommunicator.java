package communicators;

import client.proxy.ServerProxy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Command;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientCommunicator {
    private static ClientCommunicator INSTANCE = null;

    public static ClientCommunicator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientCommunicator();
        }
        return (INSTANCE);
    }

    public String encode(Command request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Command decode(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Command.class);
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
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
                    Command result = decode(respData);
                    urlConnection.disconnect();
                    return result;
                } else { // Connection Fails
                    urlConnection.disconnect();
                    return null;
                }
            } catch (Exception e) {}
        } catch (Exception e) {}
        return null;
    }
}
