package tickets.client;

import java.util.List;

import tickets.common.ChoiceDestinationCards;
import tickets.common.ClientModelUpdate;
import tickets.common.ClientStateChange;
import tickets.common.DestinationCard;
import tickets.common.ExceptionMessage;
import tickets.common.Lobby;
import tickets.common.Route;
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
    private ClientFacade modelRoot;

    public static TaskManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskManager();
        }
        return INSTANCE;
    }

    private TaskManager(){
        modelRoot = ClientFacade.getInstance();
    }

    @Override
    public void register(UserData userData) {
        ClientFacade.getInstance().setUserData(userData);
        LoginResponse response =  ServerProxy.getInstance().register(userData);
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
            modelRoot.updateLobbyList(response.getLobbyList());
            modelRoot.updateObservable(state);
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void login(UserData userData) {
        ClientFacade.getInstance().setUserData(userData);
        LoginResponse response = ServerProxy.getInstance().login(userData);
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
            modelRoot.updateLobbyList(response.getLobbyList());
            modelRoot.updateObservable(state);
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void joinLobby(String id) {
        String token = ClientFacade.getInstance().getAuthToken();
        JoinLobbyResponse response = ServerProxy.getInstance().joinLobby(id, token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
        else if (response.getException() == null) {
            modelRoot.setCurrentLobby(response.getLobby());
            ClientStateChange.ClientState stateVal = ClientStateChange.ClientState.lobby;
            ClientStateChange state = new ClientStateChange(stateVal);
            modelRoot.setCurrentLobby(response.getLobby());
            modelRoot.setPlayer(response.getPlayer());
            modelRoot.updateObservable(state);
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void createLobby(Lobby lobby) {
        String token = ClientFacade.getInstance().getAuthToken();
        JoinLobbyResponse response = ServerProxy.getInstance().createLobby(lobby, token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
        else if (response.getException() == null) {
            modelRoot.setCurrentLobby(response.getLobby());
            ClientStateChange.ClientState stateVal = ClientStateChange.ClientState.lobby;
            ClientStateChange state = new ClientStateChange(stateVal);
            modelRoot.setCurrentLobby(response.getLobby());
            modelRoot.setPlayer(response.getPlayer());
            modelRoot.updateObservable(state);
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void logout() {
        String token = ClientFacade.getInstance().getAuthToken();
        LogoutResponse response =ServerProxy.getInstance().logout(token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
        else if (response.getException() == null) {
            ClientStateChange.ClientState stateVal;
            stateVal = ClientStateChange.ClientState.login;
            ClientStateChange state = new ClientStateChange(stateVal);
            modelRoot.updateObservable(state);
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void startGame(String lobbyID) {
        String token = ClientFacade.getInstance().getAuthToken();
        StartGameResponse response = ServerProxy.getInstance().startGame(lobbyID, token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
        else if (response.getException() == null) {
            modelRoot.startGame(response.getGame(), response.getPlayerHand(), response.getDestCardOptions());
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void leaveLobby(String lobbyID) {
        String token = ClientFacade.getInstance().getAuthToken();
        LeaveLobbyResponse response = ServerProxy.getInstance().leaveLobby(lobbyID, token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
        else if (response.getException() == null) {
            ClientStateChange.ClientState stateVal;
            stateVal = ClientStateChange.ClientState.lobbylist;
            ClientStateChange state = new ClientStateChange(stateVal);
            modelRoot.setCurrentLobby(null);
            modelRoot.updateObservable(state);
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void addToChat(String message) {
        String token = ClientFacade.getInstance().getAuthToken();
        AddToChatResponse response = ServerProxy.getInstance().addToChat(message, token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() != null) {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void drawTrainCard() {
        String token = ClientFacade.getInstance().getAuthToken();
        TrainCardResponse response = ServerProxy.getInstance().drawTrainCard(token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
        else if (response.getException() == null) {
            modelRoot.getLocalPlayer().addTrainCardToHand(response.getCard());
            ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerTrainHandUpdated);
            modelRoot.updateObservable(message);
        }
        else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void drawFaceUpCard(int position) {
        String token = ClientFacade.getInstance().getAuthToken();
        TrainCardResponse response = ServerProxy.getInstance().drawFaceUpCard(position, token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() == null) {
            modelRoot.getLocalPlayer().addTrainCardToHand(response.getCard());
            ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.playerTrainHandUpdated);
            modelRoot.updateObservable(message);
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void claimRoute(Route route) {
        String token = ClientFacade.getInstance().getAuthToken();
        Response response = ServerProxy.getInstance().claimRoute(route, token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() == null) {
            //TODO: Do something with response
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void drawDestinationCard() {
        String token = ClientFacade.getInstance().getAuthToken();
        DestinationCardResponse response = ServerProxy.getInstance().drawDestinationCards(token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() == null) {
            List<DestinationCard> cards = response.getCards();
            ChoiceDestinationCards choice = new ChoiceDestinationCards();
            choice.setDestinationCards(cards);
            modelRoot.getLocalPlayer().setDestinationCardOptions(choice);
            ClientModelUpdate message = new ClientModelUpdate(ClientModelUpdate.ModelUpdate.destCardOptionsUpdated);
            modelRoot.updateObservable(message);
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void discardDestinationCard(DestinationCard discard) {
        String token = ClientFacade.getInstance().getAuthToken();
        Response response = ServerProxy.getInstance().discardDestinationCard(discard, token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() == null) {
            //TODO: Do something with response
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }

    @Override
    public void endTurn() {
        String token = ClientFacade.getInstance().getAuthToken();
        Response response = ServerProxy.getInstance().endTurn(token);
        if (response == null) {
            Exception ex = new Exception("The Server could not be reached");
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        } else if (response.getException() == null) {
            //TODO: Do something with response
        } else {
            Exception ex = response.getException();
            ExceptionMessage msg = new ExceptionMessage(ex);
            modelRoot.updateObservable(msg);
        }
    }
}
