package tickets.client;

import java.util.List;

import tickets.common.DestinationCard;
import tickets.common.Lobby;
import tickets.common.Route;
import tickets.common.TrainCard;
import tickets.common.UserData;
import tickets.common.response.AddToChatResponse;
import tickets.common.response.DestinationCardResponse;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.response.LeaveLobbyResponse;
import tickets.common.response.LoginResponse;
import tickets.common.response.LogoutResponse;
import tickets.common.response.Response;
import tickets.common.response.StartGameResponse;
import tickets.common.response.TrainCardResponse;

public class TaskManager implements ITaskManager {
    private static TaskManager INSTANCE = null;

    public static TaskManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskManager();
        }
        return INSTANCE;
    }

    private TaskManager() { }

    @Override
    public void register(UserData userData) {
        ClientFacade.getInstance().setUserData(userData);
        LoginResponse response =  ServerProxy.getInstance().register(userData);
        ResponseManager.handleResponse(response);
    }

    @Override
    public void login(UserData userData) {
        ClientFacade.getInstance().setUserData(userData);
        LoginResponse response = ServerProxy.getInstance().login(userData);
        ResponseManager.handleResponse(response);
    }

    @Override
    public void joinLobby(String id) {
        String token = ClientFacade.getInstance().getAuthToken();
        JoinLobbyResponse response = ServerProxy.getInstance().joinLobby(id, token);
        ResponseManager.handleResponse(response, false);
    }

    @Override
    public void createLobby(Lobby lobby) {
        String token = ClientFacade.getInstance().getAuthToken();
        JoinLobbyResponse response = ServerProxy.getInstance().createLobby(lobby, token);
        ResponseManager.handleResponse(response, true);
    }

    @Override
    public void logout() {
        String token = ClientFacade.getInstance().getAuthToken();
        LogoutResponse response = ServerProxy.getInstance().logout(token);
        ResponseManager.handleResponse(response);
    }

    @Override
    public void startGame(String lobbyID) {
        String token = ClientFacade.getInstance().getAuthToken();
        StartGameResponse response = ServerProxy.getInstance().startGame(lobbyID, token);
        ResponseManager.handleResponse(response);
    }

    @Override
    public void leaveLobby(String lobbyID) {
        String token = ClientFacade.getInstance().getAuthToken();
        LeaveLobbyResponse response = ServerProxy.getInstance().leaveLobby(lobbyID, token);
        ResponseManager.handleResponse(response);
    }

    @Override
    public void addToChat(String message) {
        String token = ClientFacade.getInstance().getAuthToken();
        AddToChatResponse response = ServerProxy.getInstance().addToChat(message, token);
        ResponseManager.handleResponse(response);
    }

    @Override
    public void drawTrainCard() {
        String token = ClientFacade.getInstance().getAuthToken();
        TrainCardResponse response = ServerProxy.getInstance().drawTrainCard(token);
        ResponseManager.handleResponse(response);
    }

    @Override
    public void drawFaceUpCard(int position) {
        String token = ClientFacade.getInstance().getAuthToken();
        TrainCardResponse response = ServerProxy.getInstance().drawFaceUpCard(position, token);
        ResponseManager.handleResponse(response);
    }

    @Override
    public void claimRoute(Route route, List<TrainCard> cards) {
        String token = ClientFacade.getInstance().getAuthToken();
        Response response = ServerProxy.getInstance().claimRoute(route, cards, token);
        ResponseManager.handleResponse(response);
    }

    @Override
    public void drawDestinationCard() {
        String token = ClientFacade.getInstance().getAuthToken();
        DestinationCardResponse response = ServerProxy.getInstance().drawDestinationCards(token);
        ResponseManager.handleResponse(response, false);
    }

    @Override
    public void discardDestinationCard(DestinationCard discard) {
        String token = ClientFacade.getInstance().getAuthToken();
        DestinationCardResponse response = ServerProxy.getInstance().discardDestinationCard(discard, token);
        ResponseManager.handleResponse(response, true);
    }
}
