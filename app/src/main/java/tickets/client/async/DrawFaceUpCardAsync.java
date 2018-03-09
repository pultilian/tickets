package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ModelFacade;
import tickets.client.ServerProxy;
import tickets.common.ClientModelUpdate;
import tickets.common.ExceptionMessage;
import tickets.common.response.TrainCardResponse;

class DrawFaceUpCardAsync extends AsyncTask<Object, Void, TrainCardResponse> {
    ModelFacade modelRoot;

    public DrawFaceUpCardAsync(ModelFacade setRoot) {
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
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() == null) {
            modelRoot.getLocalPlayer().addTrainCardToHand(response.getCard());
            ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerTrainHandUpdated);
            modelRoot.updateObservable(message);
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

        return;
    }
}
