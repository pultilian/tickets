package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.DestinationCardResponse;

class DrawDestinationCardAsync extends AsyncTask<String, Void, DestinationCardResponse> {
    ClientFacade modelRoot;

    public DrawDestinationCardAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public DestinationCardResponse doInBackground(String... data) {
        if (data.length != 1) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new DestinationCardResponse(error);
        }

        String authToken = data[0];
        DestinationCardResponse response = ServerProxy.getInstance().drawDestinationCards(authToken);
        return response;
    }

    @Override
    public void onPostExecute(DestinationCardResponse response) {
        ResponseManager.handleResponse(response, false);
    }
}