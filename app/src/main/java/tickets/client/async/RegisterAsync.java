
package tickets.client.async;

import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.LoginResponse;
import tickets.common.IMessage;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ServerProxy;
import tickets.client.ModelFacade;


class RegisterAsync extends AsyncTask<UserData, Void, LoginResponse> {
    ModelFacade modelRoot;

    public RegisterAsync(ModelFacade root) {
        modelRoot = root;
    }

    @Override
    public LoginResponse doInBackground(UserData... data) {
        if (data.length != 1) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new LoginResponse(error);
        }
        System.out.println("register async calling serverProxy in background");
        LoginResponse response = ServerProxy.getInstance().register(data[0]);
        System.out.println("serverProxy returned response in background");
        return response;
    }

    @Override
    public void onPostExecute(LoginResponse response) {
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
        else if (response.getException() == null) {
            modelRoot.addAuthToken(response.getAuthToken());

            ClientStateChange.ClientState stateVal;
            stateVal = ClientStateChange.ClientState.lobbylist;
            ClientStateChange state = new ClientStateChange(stateVal);
            modelRoot.updateObservable(state);
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

        return;
    }

}