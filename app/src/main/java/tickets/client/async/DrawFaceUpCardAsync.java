package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.TrainCardResponse;

class DrawFaceUpCardAsync extends AsyncTask<Object, Void, TrainCardResponse> {
    ClientFacade modelRoot;

    public DrawFaceUpCardAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public TrainCardResponse doInBackground(Object... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new TrainCardResponse(error);
        }

        int position = (int) data[0];
        String authToken = (String) data[1];
        TrainCardResponse response = ServerProxy.getInstance().drawFaceUpCard(position, authToken);
        return response;
    }

    @Override
    public void onPostExecute(TrainCardResponse response) {
        ResponseManager.handleResponse(response);
    }
}
