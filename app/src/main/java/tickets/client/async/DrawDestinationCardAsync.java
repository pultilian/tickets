package tickets.client.async;

import android.os.AsyncTask;

import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.ServerProxy;
import tickets.common.ChoiceDestinationCards;
import tickets.common.ClientModelUpdate;
import tickets.common.DestinationCard;
import tickets.common.ExceptionMessage;
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
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() == null) {
            List<DestinationCard> cards = response.getCards();
            ChoiceDestinationCards choice = new ChoiceDestinationCards();
            choice.setDestinationCards(cards);
            modelRoot.getLocalPlayer().setDestinationCardOptions(choice);
            ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.destCardOptionsUpdated);
            modelRoot.updateObservable(message);
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

        return;
    }
}