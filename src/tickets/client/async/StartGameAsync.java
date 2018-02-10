
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.client.ServerProxy;
import tickets.client.gui.presenters.ILoginPresenter;
import tickets.common.UserData;
import tickets.common.response.LoginResponse;


public class StartGameAsync /*extends AsyncTask<String, B, JoinLobbyResponse>*/ {
    ServerProxy proxy;
    ILobbyListPresenter callback;
    String authToken;


    public StartGameAsync(ServerProxy setProxy, ILobbyListPresenter setCallback, String setAuth) {
        proxy = setProxy;
        callback = setCallback;
        authToken = setAuth;
    }

    // @Override
    public JoinLobbyResponse doInBackground(LobbyData... id) {
        if (data.length != 1) {
            throw new Exception("Invalid user data passed to CreateLobbyAsync.doInBackground()");
        }
        
        JoinLobbyResponse response = proxy.joinLobby(id, authToken);
        
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