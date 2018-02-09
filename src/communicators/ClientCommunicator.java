package communicators;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import common.Command;
import common.ResultTransferObject;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ClientCommunicator {

	// Singleton pattern
	private static ClientCommunicator INSTANCE = null;
        public static ClientCommunicator getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ClientCommunicator();
		}
		return INSTANCE;
	}

	private static Gson gson = new Gson();
	private static final String URL_PREFIX = "http://" + ServerCommunicator.SERVER_HOST + ":" + ServerCommunicator.getServerPortNumber();
	private static final String HTTP_POST = "POST";

	public Object send(Command command) {
		HttpURLConnection connection = openConnection(ServerCommunicator.COMMAND_DESIGNATOR);
		sendToServerCommunicator(connection, command);
		Object result = getResult(connection);
		return result;
	}

	private HttpURLConnection openConnection(String contextIdentifier) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(URL_PREFIX + contextIdentifier);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}

	private void sendToServerCommunicator(HttpURLConnection connection, Command command) {
		try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
			gson.toJson(command, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Object getResult(HttpURLConnection connection) {
		Object result = null;
		try {
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if (connection.getContentLength() == 0) {
					System.out.println("Server response empty");
				}
				else if (connection.getContentLength() == -1) {
					try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
						ResultTransferObject transferObject = (ResultTransferObject)gson.fromJson(reader, ResultTransferObject.class);
						result = transferObject.getResult();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				throw new Exception(String.format("http code %d", connection.getResponseCode()));
			}
		} catch (JsonSyntaxException | JsonIOException e) {
			System.err.println("ERROR: json error getting result from server");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("ERROR: io error getting result from server");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
