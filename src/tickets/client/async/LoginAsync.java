
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.LoginResponse;

import tickets.client.ServerProxy;
import tickets.client.model.ClientModelRoot;


class LoginAsync /*extends AsyncTask<UserData, Void, LoginResponse>*/ {
	ClientModelRoot root;

	public LoginAsync(ClientModelRoot setRoot) {
		root = setRoot;
	}

	// @Override
	public LoginResponse doInBackground(UserData... data) {
		if (data.length != 1) {
			error = new AsyncException(this, "invalid execute() parameters");
			return new LoginResponse(error);
		}
		
		LoginResponse response = ServerProxy.getInstance().login(data[0]);
		return response;
	}

	// @Override
	public void onPostExecute(LoginResponse response) {
		if (response.getException() == null) {
			modelRoot.setUserData(userData);
			modelRoot.addAuthenticationToken(response.getAuthToken());

			stateVal = ClientStateChange.CLientState.lobbylist;
			ClientStateChange state = new ClientStateChange(stateVal);
			modelRoot.updateObserable(state);
		}
		else {
			Exception ex = response.getException();
			ExceptionMessage ex = new ExceptionMessage(ex);
			modelRoot.updateObservable(ex);
		}

		return;
	}
}