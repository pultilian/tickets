
package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.UserData;
import tickets.common.response.LoginResponse;


class LoginAsync extends AsyncTask<UserData, Void, LoginResponse> {
	ClientFacade modelRoot;

	public LoginAsync(ClientFacade setRoot) {
		modelRoot = setRoot;
	}

	@Override
	public LoginResponse doInBackground(UserData... data) {
		if (data.length != 1) {
			AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
			return new LoginResponse(error);
		}
		
		LoginResponse response = ServerProxy.getInstance().login(data[0]);
		return response;
	}

	@Override
	public void onPostExecute(LoginResponse response) {
		ResponseManager.handleResponse(response);
	}
}