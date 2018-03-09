package tickets.client.async;

import android.os.AsyncTask;

import java.util.List;

import tickets.client.ModelFacade;
import tickets.client.ServerProxy;
import tickets.common.DestinationCard;
import tickets.common.ExceptionMessage;
import tickets.common.response.DestinationCardResponse;

class DrawDestinationCardAsync extends AsyncTask<String, Void, DestinationCardResponse> {
    ModelFacade modelRoot;

    public DrawDestinationCardAsync(ModelFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public DestinationCardResponse doInBackground(String... data) {
        if (data.length != 1) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new DestinationCardResponse(error);
        }

        String authToken = data[0];
        DestinationCardResponse response = ServerProxy.getInstance().drawDestinationCard(authToken);
        return response;
    }

    @Override
    public void onPostExecute(DestinationCardResponse response) {
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() == null) {
            List<DestinationCard> cards = response.getCards();
            //TODO: present cards to client
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

        return;
    }
}