
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.Lobby;

import tickets.client.ServerProxy;
import tickets.client.gui.presenters.ILoginPresenter;
import tickets.client.model.observable.*;
import tickets.client.model.ClientModelRoot;


class CreateLobbyAsync /*extends AsyncTask<Object, Void, JoinLobbyResponse>*/ {
	ClientModelRoot modelRoot;

	public CreateLobbyAsync(ClientModelRoot setRoot) {
		modelRoot = setRoot;
	}

	public void execute(Object... args) {}

	// @Override
	public JoinLobbyResponse doInBackground(Object... data) {
		if (data.length != 2) {
			AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
			return new JoinLobbyResponse(error);
		}

		Lobby lobby = (Lobby) data[0];
		String auth = (String) data[1];
		JoinLobbyResponse response = ServerProxy.getInstance().createLobby(lobby, auth);
		
		return response;
	}

	// @Override
	public void onPostExecute(JoinLobbyResponse response) {
		if (response.getException() == null) {
			//Since the created lobby isn't 
			//
			// Lobby currentLobby = modelRoot.getLobby(response.getLobbyID());
			// currentLobby.setHistory(response.getHistory());
			// modelRoot.setCurrentLobby(currentLobby);

			ClientStateChange.ClientState stateVal;
			stateVal = ClientStateChange.ClientState.lobby;
			ClientStateChange state = new ClientStateChange(stateVal);
			modelRoot.updateObservable(state);
		}
		else {
			Exception ex = response.getException();
			ExceptionMessage msg = new ExceptionMessage(ex);
			modelRoot.updateObservable(msg);
		}
		
		return;
	}

}