package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.TrainCardResponse;

class DrawTrainCardAsync extends AsyncTask<String, Void, TrainCardResponse> {
    ClientFacade modelRoot;

    public DrawTrainCardAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public TrainCardResponse doInBackground(String... data) {
        if (data.length != 1) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new TrainCardResponse(error);
        }

        String authToken = (String) data[0];
        TrainCardResponse response = ServerProxy.getInstance().drawTrainCard(authToken);
        return response;
    }

    @Override
    public void onPostExecute(TrainCardResponse response) {
        ResponseManager.handleResponse(response);
    }
}