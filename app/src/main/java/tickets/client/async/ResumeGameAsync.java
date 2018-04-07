package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.ResumeGameResponse;

public class ResumeGameAsync extends AsyncTask<Object, Void, ResumeGameResponse> {
    ClientFacade modelRoot;

    public ResumeGameAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public ResumeGameResponse doInBackground(Object... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new ResumeGameResponse(error);
        }

        String gameID = (String) data[0];
        String auth = (String) data[1];
        ResumeGameResponse response = ServerProxy.getInstance().resumeGame(gameID, auth);

        return response;
    }

    @Override
    public void onPostExecute(ResumeGameResponse response) {
        ResponseManager.handleResponse(response);
    }
}
