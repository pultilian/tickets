package tickets.client.async;

import android.os.AsyncTask;

import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.ChoiceDestinationCards;
import tickets.common.DestinationCard;
import tickets.common.response.DestinationCardResponse;
import tickets.common.response.Response;

class DiscardDestinationCardAsync extends AsyncTask<Object, Void, DestinationCardResponse> {
    ClientFacade modelRoot;

    public DiscardDestinationCardAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public DestinationCardResponse doInBackground(Object... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new DestinationCardResponse(error);
        }

        List<DestinationCard> discards = (List<DestinationCard>) data[0];
        ChoiceDestinationCards choice = new ChoiceDestinationCards();
        choice.setDestinationCards(discards);
        String authToken = (String) data[1];
        DestinationCardResponse response = ServerProxy.getInstance().discardDestinationCard(choice, authToken);
        return response;
    }

    @Override
    public void onPostExecute(DestinationCardResponse response) {
        ResponseManager.handleResponse(response, true);
    }
}