
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.client.ServerProxy;
import tickets.client.gui.presenters.ILoginPresenter;
import tickets.common.UserData;
import tickets.common.response.LoginResponse;


class CreateLobbyAsync /*extends AsyncTask<LobbyData, B, JoinLobbyResponse>*/ {
    ServerProxy proxy;
    ILobbyListPresenter callback;


    public CreateLobbyAsync(ServerProxy setProxy, ILobbyListPresenter setCallback) {
        proxy = setProxy;
        callback = setCallback;
    }

    // @Override
    public JoinLobbyResponse doInBackground(LobbyData... data) {
        if (data.length != 1) {
            throw new Exception("Invalid user data passed to CreateLobbyAsync.doInBackground()");
        }

        String lobbyId = data[0].getId();
        String auth = data[0].getAuth();
        
        JoinLobbyResponse response = proxy.joinLobby(lobbyId, auth);
        
        return response;
    }

    // @Override
    public /* B */ Object onPostExecute(/* C */ JoinLobbyResponse response) {
        // should the presenter save data to the model?
        // or should this have a callback to ModelFacade
        // so the ModelFacade will save it?
        callback.joinLobbyCallback(false);
        
        return /* B */ Object;
    }

}