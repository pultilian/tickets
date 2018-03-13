package tickets.client;

import tickets.common.DestinationCard;
import tickets.common.Lobby;
import tickets.common.Route;
import tickets.common.UserData;

public class TaskManager implements ITaskManager {
    private static TaskManager INSTANCE = null;

    public static TaskManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskManager();
        }
        return INSTANCE;
    }

    @Override
    public void register(UserData userData) {
        ClientFacade.getInstance().setUserData(userData);
        ServerProxy.getInstance().register(userData);
    }

    @Override
    public void login(UserData userData) {
        ClientFacade.getInstance().setUserData(userData);
        ServerProxy.getInstance().login(userData);
    }

    @Override
    public void joinLobby(String id) {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().joinLobby(id, token);
    }

    @Override
    public void createLobby(Lobby lobby) {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().createLobby(lobby, token);
    }

    @Override
    public void logout() {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().logout(token);
    }

    @Override
    public void startGame(String lobbyID) {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().startGame(lobbyID, token);
    }

    @Override
    public void leaveLobby(String lobbyID) {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().leaveLobby(lobbyID, token);
    }

    @Override
    public void addToChat(String message) {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().addToChat(message, token);
    }

    @Override
    public void drawTrainCard() {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().drawTrainCard(token);
    }

    @Override
    public void drawFaceUpCard(int position) {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().drawFaceUpCard(position, token);
    }

    @Override
    public void claimRoute(Route route) {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().claimRoute(route, token);
    }

    @Override
    public void drawDestinationCard() {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().drawDestinationCards(token);
    }

    @Override
    public void discardDestinationCard(DestinationCard discard) {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().discardDestinationCard(discard, token);
    }

    @Override
    public void endTurn() {
        String token = ClientFacade.getInstance().getAuthToken();
        ServerProxy.getInstance().endTurn(token);
    }
}
