
package tickets.client.async;

// import android.os.AsyncTask;

import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.Lobby;
import tickets.common.IMessage;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ServerProxy;
import tickets.client.gui.presenters.ILoginPresenter;
import tickets.client.ModelFacade;


class CreateLobbyAsync extends AsyncTask<Object, Void, JoinLobbyResponse> {
	ModelFacade modelRoot;

	public CreateLobbyAsync(ModelFacade setRoot) {
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
		if (response == null) {
			Exception ex = new Exception("The Server could not be reached");
			ExceptionMessage msg = new ExceptionMessage(ex);
			modelRoot.updateObservable(msg);
		}
		else if (response.getException() == null) {
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