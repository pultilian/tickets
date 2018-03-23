
package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.StartGameResponse;


class StartGameAsync extends AsyncTask<String, Void, StartGameResponse> {
    ClientFacade modelRoot;

    public StartGameAsync(ClientFacade root) {
        modelRoot = root;
    }

    @Override
    public StartGameResponse doInBackground(String... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new StartGameResponse(error);
        }

        String id = data[0];
        String authToken = data[1];

        StartGameResponse response = ServerProxy.getInstance().startGame(id, authToken);
        return response;
    }

    @Override
    public void onPostExecute(StartGameResponse response) {
        ResponseManager.handleResponse(response);
    }

}