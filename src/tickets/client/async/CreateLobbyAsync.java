
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.client.ServerProxy;
import tickets.client.gui.presenters.ILoginPresenter;
import tickets.common.UserData;
import tickets.common.response.LoginResponse;


class CreateLobbyAsync /*extends AsyncTask<Object, Void, JoinLobbyResponse>*/ {
	ClientModelRoot root;

	public CreateLobbyAsync(ClientModelRoot setRoot) {
		root = setRoot;
	}

	// @Override
	public JoinLobbyResponse doInBackground(Object... data) {
		if (data.length != 2) {
			error = new AsyncException(this, "invalid execute() parameters");
			return new JoinLobbyResponse(error);
		}

		Lobby lobby = (Lobby) data[0];
		String auth = (String) data[1];
		JoinLobbyResponse response = proxy.joinLobby(lobbyId, auth);
		
		return response;
	}

	// @Override
	public void onPostExecute(JoinLobbyResponse response) {
		if (response.getException() == null) {
			Lobby currentLobby = modelRoot.getLobby(response.getLobbyID());
			currentLobby.setHistory(response.getHistory());
			modelRoot.setCurrentLobby(currentLobby);
			
			stateVal = ClientStateChange.ClientState.lobby;
			ClientStateChange state = new ClientStateChange(stateVal);
			modelRoot.updateObservable(state);
		}
		else {
			Exception ex = response.getException();
			ExceptionMessage ex = new ExceptionMessage(ex);
			modelRoot.updateObservable(ex);
		}
		
		return;
	}

}