package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ModelFacade;
import tickets.client.ServerProxy;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.response.TrainCardResponse;

class DrawTrainCardAsync extends AsyncTask<String, Void, TrainCardResponse> {
    ModelFacade modelRoot;

    public DrawTrainCardAsync(ModelFacade setRoot) {
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
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
        else if (response.getException() == null) {
            ModelFacade.getInstance().getLocalPlayer().addTrainCardToHand(response.getCard());
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

        return;
    }
}