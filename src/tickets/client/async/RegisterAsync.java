
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.LoginResponse;

import tickets.client.ServerProxy;
import tickets.client.model.CLientModelRoot;


class RegisterAsync /*extends AsyncTask<UserData, Void, LoginResponse>*/ {
	ClientModelRoot root;

	public RegisterAsync(CLientModelRoot setRoot) {
		root = setRoot;
	}

	// @Override
	public LoginResponse doInBackground(UserData... data) {
		if (data.length != 1) {
			error = new AsyncException(this, "invalid execute() parameters");
			return new LoginResponse(error);
		}

		LoginResponse response = ServerProxy.getInstance().register(data[0]);	
		return response;
	}

	// @Override
	public void onPostExecute(LoginResponse response) {
		if (response.getException() == null) {
			modelRoot.setUserData(userData);
			modelRoot.addAuthenticationToken(response.getAuthToken());

			ClientStateChange state = new ClientStateChange(ClientStateChange.CLientState.lobbylist);
			modelRoot.updateObserable(state);
		}
		else {
			// throw response.getException();
			ExceptionMessage ex = new ExceptionMessage(response.getException());
			modelRoot.updateObservable(ex);
		}	

		return;
	}

}