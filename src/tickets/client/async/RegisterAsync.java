
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.client.ServerProxy;
import tickets.client.gui.presenters.ILoginPresenter;
import tickets.common.UserData;
import tickets.common.response.LoginResponse;


public class RegisterAsync /*extends AsyncTask<UserData, B, LoginResponse>*/ {
    ServerProxy proxy;
    ILoginPresenter callback;


    public RegisterAsync(ServerProxy setProxy, ILoginPresenter setCallback) {
        proxy = setProxy;
        callback = setCallback;
    }

    // @Override
    public LoginResponse doInBackground(UserData... data) {
        if (data.length != 1) {
            throw new Exception("Invalid user data passed to RegisterAsync.doInBackground()");
        }
        
        LoginResponse response = proxy.register(data[0]);
        
        return response;
    }

    // @Override
    public /* B */ Object onPostExecute(/* C */ Object val) {
        // should the presenter save data to the model?
        // or should this have a callback to ModelFacade
        // so the ModelFacade will save it?
        callback.registerCallback(false);
        
        return /* B */ Object;
    }

}