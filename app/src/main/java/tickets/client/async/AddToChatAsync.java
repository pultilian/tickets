package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ServerProxy;
import tickets.common.ExceptionMessage;
import tickets.common.response.AddToChatResponse;

/**
 * Created by Derek Mines on 2/23/2018.
 */

public class AddToChatAsync extends AsyncTask<String, Void, AddToChatResponse> {
    ClientFacade modelRoot;

    public AddToChatAsync(ClientFacade root) {
        modelRoot = root;
    }

    @Override
    public AddToChatResponse doInBackground(String... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new AddToChatResponse(error);
        }

        String message = data[0];
        String authToken = data[1];
        AddToChatResponse response = ServerProxy.getInstance().addToChat(message, authToken);
        return response;
    }

    @Override
    public void onPostExecute(AddToChatResponse response) {
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() != null) {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

        return;
    }
}
