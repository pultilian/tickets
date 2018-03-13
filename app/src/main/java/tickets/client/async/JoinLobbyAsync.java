
package tickets.client.async;

import android.os.AsyncTask;

import tickets.common.response.JoinLobbyResponse;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ServerProxy;
import tickets.client.ClientFacade;


class JoinLobbyAsync extends AsyncTask<String, Void, JoinLobbyResponse> {
	ClientFacade modelRoot;

	public JoinLobbyAsync(ClientFacade setRoot) {
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
		if (response == null) {
			Exception ex = new Exception("The Server could not be reached");
			ExceptionMessage msg = new ExceptionMessage(ex);
			modelRoot.updateObservable(msg);
		}
		else if (response.getException() == null) {
			modelRoot.setCurrentLobby(response.getLobby());

			ClientStateChange.ClientState stateVal = ClientStateChange.ClientState.lobby;
			ClientStateChange state = new ClientStateChange(stateVal);
			modelRoot.setCurrentLobby(response.getLobby());
			modelRoot.setPlayer(response.getPlayer());
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