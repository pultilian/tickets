
package tickets.testing;

import tickets.client.ClientCommunicator;
import tickets.common.Command;
import tickets.common.Lobby;
import tickets.common.UserData;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.response.LoginResponse;


// these test cases require that the ServerCommunicator be hard reset
public class TestClient {
	public static void main(String[] args) {
		System.out.println("starting TestClient.main");
		TestClient test = new TestClient();
		test.runTestCases();
	}


	private ClientCommunicator clientCommunicator;
	private String authToken;

	public TestClient() {
		clientCommunicator = ClientCommunicator.getInstance();

		LoginResponse response = login(new UserData("www", "www"));
		assert(response != null);
		// assert(response.getException() == null);
		if (response.getException() != null) {
			System.out.println("Login failed: " + response.getException().toString());
			System.out.println("\tattempting to register");
			response = register(new UserData("www", "www"));
			assert(response != null);
			assert(response.getException() == null);
		}

		authToken = response.getAuthToken();
		assert(authToken != null);
		assert(! authToken.equals(""));
	}


	public void runTestCases() {
		testAddLobby();
		testBlankLogin();
		testInvalidLogin();
		testGetLobbyList();

		System.out.println("All Tests passed");
		return;
	}

// test cases
	public void testGetLobbyList() {
		LoginResponse resp = register(new UserData("diffName", "diffPassword"));
		assert(resp != null);
		if (resp.getException() != null) {
			System.out.println(resp.getException().toString());
		}
		assert(resp.getException() == null);

		System.out.println("Returned Lobby List: ");
		System.out.println(resp.getLobbyList().toString());

		assert(resp.getLobbyList() != null);
		for (Lobby l : resp.getLobbyList()) {
			assert(l != null);
		}

		return;
	}

	public void testAddLobby() {
		Lobby l1 = new Lobby("Lobby 1", 2);
		Lobby l2 = new Lobby("Lobby 2", 3);
		Lobby l3 = new Lobby("Lobby 3", 4);
		Lobby l4 = new Lobby("Lobby 4", 5);
		
		JoinLobbyResponse r1 = createLobby(l1, authToken);
		JoinLobbyResponse r2 = createLobby(l2, authToken);
		JoinLobbyResponse r3 = createLobby(l3, authToken);
		JoinLobbyResponse r4 = createLobby(l4, authToken);

		assert(r1 != null);
		assert(r2 != null);
		assert(r3 != null);
		assert(r4 != null);

		assert(r1.getException() == null);
		assert(r2.getException() == null);
		assert(r3.getException() == null);
		assert(r4.getException() == null);

		assert(r1.getLobby() != null);
		assert(r1.getLobby().getName().equals("Lobby 1"));
		assert(r1.getLobby().getMaxMembers() == 2);

		assert(r2.getLobby() != null);
		assert(r2.getLobby().getName().equals("Lobby 2"));
		assert(r2.getLobby().getMaxMembers() == 3);

		assert(r3.getLobby() != null);
		assert(r3.getLobby().getName().equals("Lobby 3"));
		assert(r3.getLobby().getMaxMembers() == 4);

		assert(r4.getLobby() != null);
		assert(r4.getLobby().getName().equals("Lobby 4"));
		assert(r4.getLobby().getMaxMembers() == 5);

		return;
	}

	public void testBlankLogin() {
		UserData emptyStringUN = new UserData("", "password");
		UserData whiteSpaceUN = new UserData("    ", "password");

		UserData emptyStringPW = new UserData("username", "");
		UserData whiteSpacePW = new UserData("username", "    ");

		LoginResponse resp1 = login(emptyStringUN);
		LoginResponse resp2 = login(whiteSpaceUN);
		LoginResponse resp3 = login(emptyStringPW);
		LoginResponse resp4 = login(whiteSpacePW);

		assert(resp1 != null);
		assert(resp2 != null);
		assert(resp3 != null);
		assert(resp4 != null);

		assert(resp1.getException() != null);
		assert(resp2.getException() != null);
		assert(resp3.getException() != null);
		assert(resp4.getException() != null);

		return;
	}

	public void testInvalidLogin() {
		UserData symbolUN = new UserData("sdlfkj##$@sd", "password");
		UserData whiteSpaceUN = new UserData("name with whitespace", "password");

		UserData symbolPW = new UserData("username", "sldkfj%^%^%sldkfj");
		UserData whiteSpacePW = new UserData("username", "password with whitespace");

		LoginResponse resp1 = login(symbolUN);
		LoginResponse resp2 = login(whiteSpaceUN);
		LoginResponse resp3 = login(symbolPW);
		LoginResponse resp4 = login(whiteSpacePW);

		assert(resp1 != null);
		assert(resp2 != null);
		assert(resp3 != null);
		assert(resp4 != null);

		assert(resp1.getException() != null);
		assert(resp2.getException() != null);
		assert(resp3.getException() != null);
		assert(resp4.getException() != null);

		return;
	}



	public LoginResponse login(UserData userData) {
		Object[] parameters = {userData};
		String[] parameterTypes = {UserData.class.getName()};
		Command command = new Command("login", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (LoginResponse) result;
	}

	public LoginResponse register(UserData userData) {
		Object[] parameters = {userData};
		String[] parameterTypes = {UserData.class.getName()};
		Command command = new Command("register", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (LoginResponse) result;
	}




	public JoinLobbyResponse createLobby(Lobby lobby, String authToken) {
		Object[] parameters = {lobby, authToken};
		String[] parameterTypes = {Lobby.class.getName(), String.class.getName()};
		Command command = new Command("createLobby", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (JoinLobbyResponse) result;
	}
}