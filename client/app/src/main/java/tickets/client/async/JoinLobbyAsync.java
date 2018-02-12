
package tickets.client.async;

import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.Lobby;

import tickets.client.ServerProxy;
import tickets.client.gui.presenters.ILoginPresenter;
import tickets.client.model.observable.*;
import tickets.client.model.ClientModelRoot;


class JoinLobbyAsync extends AsyncTask<String, Void, JoinLobbyResponse> {
	ClientModelRoot modelRoot;

	public JoinLobbyAsync(ClientModelRoot setRoot) {
		modelRoot = setRoot;
	}

	@Override
	public JoinLobbyResponse doInBackground(String... data) {
		if (data.length != 2) {
			AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
			return new JoinLobbyResponse(error);
		}

		String lobbyId = data[0];
		String authToken = data[1];		
		JoinLobbyResponse response = ServerProxy.getInstance().joinLobby(lobbyId, authToken);
		return response;
	}

	@Override
	public void onPostExecute(JoinLobbyResponse response) {
		if (response.getException() == null) {
			Lobby currentLobby = modelRoot.getLobby(response.getLobbyID());
			currentLobby.setHistory(response.getLobbyHistory());
			modelRoot.setCurrentLobby(currentLobby);

			ClientStateChange.ClientState stateVal = ClientStateChange.ClientState.lobby;
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