package tickets.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import tickets.common.Command;
import tickets.common.ResultTransferObject;

public class ServerCommunicator {

	private HttpServer server;
	
	private static final int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	public static final String COMMAND_DESIGNATOR = "/command";
	public static final String DEFAULT_DESIGNATOR = "/";

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
			Object result = null;
			try(InputStreamReader reader = new InputStreamReader(exchange.getRequestBody())) {
				Command command = new Command(reader);
				result = command.execute(ServerFacade.getInstance());
			}
			try(OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody())) {
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				ResultTransferObject transferObject = new ResultTransferObject(result.getClass().getName(), result);
				gson.toJson(transferObject, writer);
				writer.close();
			}
		}
	};

	private void run() {
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER), MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {
			System.err.println("Could not create HTTP server: " + e.getMessage());
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
