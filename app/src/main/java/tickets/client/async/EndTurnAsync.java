package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.response.Response;

class EndTurnAsync  extends AsyncTask<String, Void, Response> {
    ClientFacade modelRoot;

    public EndTurnAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public Response doInBackground(String... data) {
        if (data.length != 1) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new Response(error);
        }

        String authToken = data[0];
        Response response = ServerProxy.getInstance().endTurn(authToken);
        return response;
    }

    @Override
    public void onPostExecute(Response response) {
        ResponseManager.handleResponse(response);
    }
}