package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ServerProxy;
import tickets.common.DestinationCard;
import tickets.common.ExceptionMessage;
import tickets.common.response.DestinationCardResponse;
import tickets.common.response.Response;

class DiscardDestinationCardAsync extends AsyncTask<Object, Void, Response> {
    ClientFacade modelRoot;

    public DiscardDestinationCardAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public Response doInBackground(Object... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new DestinationCardResponse(error);
        }

        DestinationCard discard = (DestinationCard) data[0];
        String authToken = (String) data[1];
        Response response = ServerProxy.getInstance().discardDestinationCard(discard, authToken);
        return response;
    }

    @Override
    public void onPostExecute(Response response) {
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() == null) {
            //TODO: Do something with response
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

        return;
    }
}