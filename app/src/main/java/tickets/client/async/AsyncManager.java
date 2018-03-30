package tickets.client.async;

import tickets.client.ClientFacade;
import tickets.client.ITaskManager;
import tickets.common.DestinationCard;
import tickets.common.Lobby;
import tickets.common.Route;
import tickets.common.TrainCardWrapper;
import tickets.common.UserData;

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
public class AsyncManager implements ITaskManager {
//----------------------------------------------------------------------------
// Singleton
    private static AsyncManager INSTANCE = null;
    private ClientFacade root;
    public static AsyncManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new AsyncManager();
        }
        return INSTANCE;
    }
    private AsyncManager() {
        root = ClientFacade.getInstance();
    }

//----------------------------------------------------------------------------
// Async Tasks
    public void register(UserData userData) {
        root.setUserData(userData);
        RegisterAsync task = new RegisterAsync(root);
        task.execute(userData);
    }

    public void login(UserData userData) {
        root.setUserData(userData);
        LoginAsync task = new LoginAsync(root);
        task.execute(userData);
    }

    public void joinLobby(String id) {
        String authToken = root.getAuthToken();
        JoinLobbyAsync task = new JoinLobbyAsync(root);
        task.execute(id, authToken);
    }

    public void createLobby(Lobby lobby) {
        String authToken = root.getAuthToken();
        CreateLobbyAsync task = new CreateLobbyAsync(root);
        task.execute(lobby, authToken);
    }

    public void logout() {
        String authToken = root.getAuthToken();
        LogoutAsync task = new LogoutAsync(root);
        task.execute(authToken);
    }

    public void startGame(String lobbyID) {
        String authToken = root.getAuthToken();
        StartGameAsync task = new StartGameAsync(root);
        task.execute(lobbyID, authToken);
    }

    public void leaveLobby(String lobbyID) {
        String authToken = root.getAuthToken();
        LeaveLobbyAsync task = new LeaveLobbyAsync(root);
        task.execute(lobbyID, authToken);
    }

    public void addToChat(String message) {
        String authToken = root.getAuthToken();
        AddToChatAsync task = new AddToChatAsync(root);
        task.execute(message, authToken);
    }

    public void drawTrainCard() {
        String authToken = root.getAuthToken();
        DrawTrainCardAsync task = new DrawTrainCardAsync(root);
        task.execute(authToken);
    }

    public void drawFaceUpCard(int position) {
        String authToken = root.getAuthToken();
        DrawFaceUpCardAsync task = new DrawFaceUpCardAsync(root);
        task.execute(position, authToken);
    }

    public void claimRoute(Route route, TrainCardWrapper cards) {
        String authToken = root.getAuthToken();
        ClaimRouteAsync task = new ClaimRouteAsync(root);
        task.execute(route, cards, authToken);
    }

    public void drawDestinationCard() {
        String authToken = root.getAuthToken();
        DrawDestinationCardAsync task = new DrawDestinationCardAsync(root);
        task.execute(authToken);
    }

    public void discardDestinationCard(DestinationCard discard) {
        String authToken = root.getAuthToken();
        DiscardDestinationCardAsync task = new DiscardDestinationCardAsync(root);
        task.execute(discard, authToken);
    }
}