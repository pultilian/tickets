package tickets.client.async;

import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.ResponseManager;
import tickets.client.ServerProxy;
import tickets.common.Route;
import tickets.common.TrainCard;
import tickets.common.response.ClaimRouteResponse;
import tickets.common.response.Response;

class ClaimRouteAsync extends AsyncTask<Object, Void, ClaimRouteResponse> {
    ClientFacade modelRoot;

    public ClaimRouteAsync(ClientFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public ClaimRouteResponse doInBackground(Object... data) {
        if (data.length != 3) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new ClaimRouteResponse(error);
        }

        Route route = (Route) data[0];
        List<TrainCard> cards = Arrays.asList((TrainCard[]) data[1]);
        String authToken = (String) data[2];
        return ServerProxy.getInstance().claimRoute(route, cards, authToken);
    }

    @Override
    public void onPostExecute(ClaimRouteResponse response) {
        ResponseManager.handleResponse(response);
    }
}