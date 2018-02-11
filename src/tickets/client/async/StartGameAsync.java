
package tickets.client.async;

// import android.os.AsyncTask;

import tickets.common.UserData;
import tickets.common.response.JoinLobbyResponse;

import tickets.client.ServerProxy;
import tickets.client.model.ClientModelRoot;


class StartGameAsync /*extends AsyncTask<String, B, JoinLobbyResponse>*/ {
    ClientModelRoot modelRoot;

    public StartGameAsync(ClientModelRoot root) {
        modelRoot = root;
    }

    public void execute(String... args) {}

    // @Override
    JoinLobbyResponse doInBackground(String... data) {
        if (data.length != 2) {
            AsyncException error = new AsyncException(this.getClass(), "invalid execute() parameters");
            return new JoinLobbyResponse(error);
        }

        String id = data[0];
        String authToken = data[1];

        JoinLobbyResponse response = ServerProxy.getInstance().joinLobby(id, authToken);
        return response;
    }

    // @Override
    void onPostExecute(JoinLobbyResponse response) {
        //
        return;
    }

}