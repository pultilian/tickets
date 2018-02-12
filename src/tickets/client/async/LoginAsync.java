
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.LoginResponse;
import tickets.common.IMessage;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ServerProxy;
import tickets.client.ModelFacade;


class LoginAsync /*extends AsyncTask<UserData, Void, LoginResponse>*/ {
	ModelFacade modelRoot;

	public LoginAsync(ModelFacade setRoot) {
		modelRoot = setRoot;
	}

	public void execute(UserData... args) {}

	// @Override
	public LoginResponse doInBackground(UserData... data) {
		if (data.length != 1) {
			AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
			return new LoginResponse(error);
		}
		
		LoginResponse response = ServerProxy.getInstance().login(data[0]);
		return response;
	}

	// @Override
	public void onPostExecute(LoginResponse response) {
		if (response.getException() == null) {
			modelRoot.addAuthToken(response.getAuthToken());

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