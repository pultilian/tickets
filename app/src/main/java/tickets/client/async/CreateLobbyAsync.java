
package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.Lobby;
import tickets.common.response.JoinLobbyResponse;


class CreateLobbyAsync extends AsyncTask<Object, Void, JoinLobbyResponse> {
	ClientFacade modelRoot;

	public CreateLobbyAsync(ClientFacade setRoot) {
		modelRoot = setRoot;
	}

	@Override
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

	@Override
	public void onPostExecute(JoinLobbyResponse response) {
		ResponseManager.handleResponse(response, true);
	}

}