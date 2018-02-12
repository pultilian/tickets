
package tickets.client.async;

import tickets.common.response.LogoutResponse;
import tickets.common.IMessage;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ModelFacade;
import tickets.client.ServerProxy;


public class LogoutAsync /*extends AsyncTask<String, Void, LogoutResponse>*/ {
	ModelFacade modelRoot;

	public LogoutAsync(ModelFacade root) {
		modelRoot = root;
	}

	public void execute(Object... args) {}

	// @Override
	public LogoutResponse doInBackground(String... data) {
		if (data.length != 1) {
			AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
			return new LogoutResponse(error);
		}
		
		LogoutResponse response = ServerProxy.getInstance().logout(data[0]);
		return response;
	}

	// @Override
	public void onPostExecute(LogoutResponse response) {
		if (response.getException() == null) {
			ClientStateChange.ClientState stateVal;
			stateVal = ClientStateChange.ClientState.login;
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