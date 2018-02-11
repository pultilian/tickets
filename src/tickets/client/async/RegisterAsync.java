
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.LoginResponse;

import tickets.client.ServerProxy;
import tickets.client.model.ClientModelRoot;
import tickets.client.model.observable.*;


class RegisterAsync /*extends AsyncTask<UserData, Void, LoginResponse>*/ {
	ClientModelRoot modelRoot;

	public RegisterAsync(ClientModelRoot root) {
		modelRoot = root;
	}

	public void execute(UserData... args) {}

	// @Override
	public LoginResponse doInBackground(UserData... data) {
		if (data.length != 1) {
			AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
			return new LoginResponse(error);
		}

		LoginResponse response = ServerProxy.getInstance().register(data[0]);	
		return response;
	}

	// @Override
	public void onPostExecute(LoginResponse response) {
		if (response.getException() == null) {
			modelRoot.addAuthenticationToken(response.getAuthToken());

			ClientStateChange.ClientState stateVal;
			stateVal = ClientStateChange.ClientState.lobbylist;
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