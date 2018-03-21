package tickets.client.async;

import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.Route;
import tickets.common.TrainCard;
import tickets.common.response.Response;

class ClaimRouteAsync extends AsyncTask<Object, Void, Response> {
    ClientFacade modelRoot;

    public ClaimRouteAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public Response doInBackground(Object... data) {
        if (data.length != 3) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new Response(error);
        }

        Route route = (Route) data[0];
        List<TrainCard> cards = Arrays.asList((TrainCard[]) data[1]);
        String authToken = (String) data[2];
        Response response = ServerProxy.getInstance().claimRoute(route, cards, authToken);
        return response;
    }

    @Override
    public void onPostExecute(Response response) {
        ResponseManager.handleResponse(response);
    }
}