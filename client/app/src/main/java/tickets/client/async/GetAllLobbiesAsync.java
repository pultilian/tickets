
package tickets.client.async;

import android.os.AsyncTask;

import tickets.common.response.data.LobbyListData;

import tickets.client.model.ClientModelRoot;
import tickets.client.ServerProxy;
import tickets.client.model.observable.*;


public class GetAllLobbiesAsync extends AsyncTask<String, Void, LobbyListData> {
    ClientModelRoot modelRoot;

    public GetAllLobbiesAsync(ClientModelRoot root) {
        modelRoot = root;
    }

    @Override
    public LobbyListData doInBackground(String... args) {
        if (args.length != 1) {
            AsyncException error = new AsyncException(this.getClass());
            return new LobbyListData(error);
        }

        String authToken = args[0];
        LobbyListData response = ServerProxy.getInstance().getAllLobbies(authToken);
        return response;
    }

    @Override
    public void onPostExecute(LobbyListData response) {
        if (response.getException() == null) {
            modelRoot.updateLobbyList(response.getData());
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }

        return;
    }

}