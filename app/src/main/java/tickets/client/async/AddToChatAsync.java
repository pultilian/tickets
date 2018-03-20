package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.AddToChatResponse;

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
        ResponseManager.handleResponse(response);
    }
}
