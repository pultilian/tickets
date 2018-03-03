
package tickets.client.async;

import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.IMessage;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ServerProxy;
import tickets.client.ModelFacade;
import tickets.common.response.StartGameResponse;


class StartGameAsync extends AsyncTask<String, Void, StartGameResponse> {
    ModelFacade modelRoot;

    public StartGameAsync(ModelFacade root) {
        modelRoot = root;
    }

    @Override
    public StartGameResponse doInBackground(String... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new StartGameResponse(error);
        }

        String id = data[0];
        String authToken = data[1];

        StartGameResponse response = ServerProxy.getInstance().startGame(id, authToken);
        return response;
    }

    @Override
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
        else if (response.getException() == null) {
            ClientStateChange.ClientState stateVal;
            stateVal = ClientStateChange.ClientState.game;
            ClientStateChange state = new ClientStateChange(stateVal);
            modelRoot.updateObservable(state);
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

            return;
    }

}