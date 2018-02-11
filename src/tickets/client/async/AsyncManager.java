package tickets.client.async;

import tickets.common.Lobby;
import tickets.common.UserData;

import tickets.client.model.ClientModelRoot;

// AsyncTask<Params, Progress, Result>
//
//  - main (GUI) thread
//    task.execute(Params... params) -> returns void
//  - background thread
//    task.doInBackground(Params... params) -> returns Result
//      -> publishProgress(Progress... progress) -> returns void
//           - main thread: task.onProgressUpdate(Progress... progress) -> returns void
//      -> return Result
//  - main thread
//    task.onPostExecute(Result result) -> returns void


// creates and manages AsyncTask objects for communicating with the server over the network
public class AsyncManager {
	private ClientModelRoot root;

	public AsyncManager(ClientModelRoot setRoot) {
		root = setRoot;
	}

	public void register(UserData userData) {
		RegisterAsync task = new RegisterAsync(root);
		task.execute(userData);
		return;
	}

	public void login(UserData userData) {
		LoginAsync task = new LoginAsync(root);
		task.execute(userData);
		return;
	}

	public void joinLobby(String id, String auth) {
		JoinLobbyAsync task = new JoinLobbyAsync(root);
		task.execute(id, auth);
		return;
	}

	public void createLobby(Lobby lobby) {
		CreateLobbyAsync task = new CreateLobbyAsync(root);
		task.execute(lobby);
		return;
	}

	public void logout() {
		LogoutAsync task = new LogoutAsync(root);
		task.execute();
		return;
	}

	public void startGame(Lobby id) {
		StartGameAsync task = new StartGameAsync(root);
		task.execute();
		return;
	}

	public void leaveLobby(UserData user) {
		return;
	}
	
	public void addGuest(Lobby id) {
		return;
	}

	public void takeTurn(String playerID) {
		return;
	}

}