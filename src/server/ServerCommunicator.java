package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import com.google.gson.Gson;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import tickets.common.ResultTransferObject;
import tickets.common.Command;

public class ServerCommunicator {

	private HttpServer server;
	
	private static final int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	public static final String COMMAND_DESIGNATOR = "/command";
	public static final String DEFAULT_DESIGNATOR = "/";
	public static final String SERVER_HOST = "localhost";

	private static Gson gson = new Gson();

	private ServerCommunicator() { }
	
	public static int getServerPortNumber() {
		return SERVER_PORT_NUMBER;
	}

	private HttpHandler defaultHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException { }
	};

	private HttpHandler commandHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			System.out.println("Handling operation...");
			Object result = null;
			try(InputStreamReader reader = new InputStreamReader(exchange.getRequestBody())) {
				System.out.println("  Processing command...");
				Command command = new Command(reader);
				result = command.execute();
				System.out.println("  Command executed.");
			}
			try(OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody())) {
				System.out.println("  Preparing server response...");
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				ResultTransferObject transferObject = new ResultTransferObject(result.getClass().getName(), result);
				System.out.println("  Returning result as json...");
				gson.toJson(transferObject, writer);
				writer.close();
				System.out.println("Operation handled.");
			}
		}
	};

	private void run() {
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER), MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {
			System.out.println("Could not create HTTP server: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		server.setExecutor(null);
		server.createContext(COMMAND_DESIGNATOR, commandHandler);
		server.createContext(DEFAULT_DESIGNATOR, defaultHandler);
		server.start();
	}

	public static void main(String[] args) {
		new ServerCommunicator().run();
		System.out.println("Server running...");
	}

}
