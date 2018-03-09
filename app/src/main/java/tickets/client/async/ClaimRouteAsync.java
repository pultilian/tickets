package tickets.client.async;

import android.os.AsyncTask;

import tickets.client.ModelFacade;
import tickets.client.ServerProxy;
import tickets.common.ExceptionMessage;
import tickets.common.Route;
import tickets.common.response.Response;
import tickets.common.response.TrainCardResponse;

class ClaimRouteAsync extends AsyncTask<Object, Void, Response> {
    ModelFacade modelRoot;

    public ClaimRouteAsync(ModelFacade setRoot) {
        modelRoot = setRoot;
    }

    @Override
    public Response doInBackground(Object... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new Response(error);
        }

        Route route = (Route) data[0];
        String authToken = (String) data[1];
        Response response = ServerProxy.getInstance().claimRoute(route, authToken);
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