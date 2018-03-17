
package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.LogoutResponse;


public class LogoutAsync extends AsyncTask<String, Void, LogoutResponse> {
	ClientFacade modelRoot;

	public LogoutAsync(ClientFacade root) {
		modelRoot = root;
	}

	@Override
	public LogoutResponse doInBackground(String... data) {
		if (data.length != 1) {
			AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
			return new LogoutResponse(error);
		}
		
		LogoutResponse response = ServerProxy.getInstance().logout(data[0]);
		return response;
	}

	@Override
	public void onPostExecute(LogoutResponse response) {
		ResponseManager.handleResponse(response);
	}
}