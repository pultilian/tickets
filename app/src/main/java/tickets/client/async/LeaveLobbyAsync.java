
package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.LeaveLobbyResponse;


public class LeaveLobbyAsync extends AsyncTask<String, Void, LeaveLobbyResponse> {
	ClientFacade modelRoot;

	public LeaveLobbyAsync(ClientFacade setRoot) {
		modelRoot = setRoot;
	}

    @Override
	public LeaveLobbyResponse doInBackground(String... data) {
		if (data.length != 2) {
			AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
			return new LeaveLobbyResponse(error);
		}
		
		String lobbyID = data[0];
		String authToken = data[1];
		LeaveLobbyResponse response = ServerProxy.getInstance().leaveLobby(lobbyID, authToken);
		return response;
	}

	@Override
	public void onPostExecute(LeaveLobbyResponse response) {
		ResponseManager.handleResponse(response);
	}
}