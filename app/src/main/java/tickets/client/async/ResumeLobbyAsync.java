package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.ResumeLobbyResponse;

public class ResumeLobbyAsync extends AsyncTask<Object, Void, ResumeLobbyResponse> {
    ClientFacade modelRoot;

    public ResumeLobbyAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public ResumeLobbyResponse doInBackground(Object... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new ResumeLobbyResponse(error);
        }

        String lobbyID = (String) data[0];
        String auth = (String) data[1];
        ResumeLobbyResponse response = ServerProxy.getInstance().resumeLobby(lobbyID, auth);

        return response;
    }

    @Override
    public void onPostExecute(ResumeLobbyResponse response) {
        ResponseManager.handleResponse(response);
    }
}
