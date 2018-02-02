import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Pultilian on 1/31/2018.
 */
public class ServerCommunicator {
    private CommandHandler commandHandler;
    private ServerFacade server;

    public void startServer(){}

    public Command sendCommand(String urlSuffix, Command command){
        return null;
    }

    public String encode(Command command){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(command);
    }

    public Command decode(String body) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Command.class);
    }

    static String read(InputStreamReader is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public class CommandHandler implements HttpHandler {
        public void handle(HttpExchange data) throws IOException {
            InputStreamReader isr = new InputStreamReader(data.getRequestBody(), "utf-8");
            String string = read(isr);
            Command command;
            String result;
            try {
                command = decode(string);
                command.execute();
                result = encode(command);
                data.sendResponseHeaders(200, 0); // for success
                OutputStream os = data.getResponseBody();
                os.write(result.getBytes());
                os.close();
            } catch (Exception e) {
                data.sendResponseHeaders(200, 0); // for success
                OutputStream os = data.getResponseBody();
                os.write("Error".getBytes());
                os.close();
            }
        }
    }
}
