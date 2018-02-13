
package tickets.client.async;

import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.IMessage;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ServerProxy;
import tickets.client.ModelFacade;


class StartGameAsync extends AsyncTask<String, Void, JoinLobbyResponse> {
    ModelFacade modelRoot;

    public StartGameAsync(ModelFacade root) {
        modelRoot = root;
    }

    @Override
    public JoinLobbyResponse doInBackground(String... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new JoinLobbyResponse(error);
        }

        String id = data[0];
        String authToken = data[1];

        JoinLobbyResponse response = ServerProxy.getInstance().joinLobby(id, authToken);
        return response;
    }

    @Override
    public void onPostExecute(JoinLobbyResponse response) {
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

            return;
    }

}