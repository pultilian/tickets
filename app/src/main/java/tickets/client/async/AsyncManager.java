package tickets.client.async;

import tickets.common.DestinationCard;
import tickets.common.Lobby;
import tickets.common.Route;
import tickets.common.UserData;

import tickets.client.ModelFacade;

// AsyncTask<Params, Progress, Result>
//
//  - main (GUI) thread
//    task.execute(Params... params) -> returns void
//  - background thread
//    task.doInBackground(Params... params) -> returns Result
//      -> publishProgress(Progress... progress) -> returns void
//           - main thread: task.onProgressUpdate(Progress... progress) -> returns void
//      -> return Result
//  - main thread
//    task.onPostExecute(Result result) -> returns void


// creates and manages AsyncTask objects for communicating with the server over the network
public class AsyncManager {
    private ModelFacade root;

    public AsyncManager(ModelFacade setRoot) {
        root = setRoot;
    }

    public void register(UserData userData) {
        RegisterAsync task = new RegisterAsync(root);
        System.out.println("AsyncManager executing task and returning");
        task.execute(userData);
        return;
    }

    public void login(UserData userData) {
        LoginAsync task = new LoginAsync(root);
        task.execute(userData);
        return;
    }

    public void joinLobby(String id, String authToken) {
        JoinLobbyAsync task = new JoinLobbyAsync(root);
        task.execute(id, authToken);
        return;
    }

    public void createLobby(Lobby lobby, String authToken) {
        CreateLobbyAsync task = new CreateLobbyAsync(root);
        task.execute(lobby, authToken);
        return;
    }

    public void logout(String authToken) {
        LogoutAsync task = new LogoutAsync(root);
        task.execute(authToken);
        return;
    }

    public void startGame(String lobbyID, String authToken) {
        StartGameAsync task = new StartGameAsync(root);
        task.execute(lobbyID, authToken);
        return;
    }

    public void leaveLobby(String lobbyID, String authToken) {
        LeaveLobbyAsync task = new LeaveLobbyAsync(root);
        task.execute(lobbyID, authToken);
        return;
    }

    public void addToChat(String message, String authToken) {
        AddToChatAsync task = new AddToChatAsync(root);
        task.execute(message, authToken);
    }

    public void drawTrainCard(String authToken) {
        DrawTrainCardAsync task = new DrawTrainCardAsync(root);
        task.execute(authToken);
    }

    public void drawFaceUpCard(int position, String authToken) {
        DrawFaceUpCardAsync task = new DrawFaceUpCardAsync(root);
        task.execute(position, authToken);
    }

    public void claimRoute(Route route, String authToken) {
        ClaimRouteAsync task = new ClaimRouteAsync(root);
        task.execute(route, authToken);
    }

    public void drawDestinationCard(String authToken) {
        DrawDestinationCardAsync task = new DrawDestinationCardAsync(root);
        task.execute(authToken);
    }

    public void discardDestinationCard(DestinationCard discard, String authToken) {
        DiscardDestinationCardAsync task = new DiscardDestinationCardAsync(root);
        task.execute(authToken);
    }

    public void endTurn(String authToken) {
        EndTurnAsync task = new EndTurnAsync(root);
        task.execute(authToken);
    }

}