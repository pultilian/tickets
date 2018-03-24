
package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.JoinLobbyResponse;


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
		ResponseManager.handleResponse(response, false);
	}
}