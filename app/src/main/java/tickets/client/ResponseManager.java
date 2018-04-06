package tickets.client;

import java.util.List;

import tickets.common.ChoiceDestinationCards;
import tickets.common.ClientModelUpdate;
import tickets.common.ClientStateChange;
import tickets.common.DestinationCard;
import tickets.common.ExceptionMessage;
import tickets.common.Player;
import tickets.common.response.ClaimRouteResponse;
import tickets.common.response.DestinationCardResponse;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.response.LeaveLobbyResponse;
import tickets.common.response.LoginResponse;
import tickets.common.response.LogoutResponse;
import tickets.common.response.Response;
import tickets.common.response.StartGameResponse;
import tickets.common.response.TrainCardResponse;

public final class ResponseManager {

    private static void handleException(Exception ex){
        ExceptionMessage msg = new ExceptionMessage(ex);
        ClientFacade.getInstance().updateObservable(msg);
    }

    public static void handleResponse(LoginResponse response) {
        if (response == null) {
            handleException(new Exception("The Server could not be reached"));
        }
        else if (response.getException() == null) {
            ClientFacade.getInstance().addAuthToken(response.getAuthToken());
            ClientStateChange.ClientState stateVal;
            stateVal = ClientStateChange.ClientState.lobbylist;
            ClientStateChange state = new ClientStateChange(stateVal);
            ClientFacade.getInstance().updateLobbyList(response.getLobbyList());
            ClientFacade.getInstance().updateObservable(state);
        }
        else {
            handleException(response.getException());
        }
    }

    public static void handleResponse(JoinLobbyResponse response, boolean created) {
        if (response == null) {
            handleException(new Exception("The Server could not be reached"));
        }
        else if (response.getException() == null) {
            ClientFacade.getInstance().setCurrentLobby(response.getLobby().getId());
            ClientStateChange.ClientState stateVal = ClientStateChange.ClientState.lobby;
            ClientStateChange state = new ClientStateChange(stateVal);
            if (created)
                ClientFacade.getInstance().addLobbyToList(response.getLobby());
            ClientFacade.getInstance().setPlayer(response.getPlayer());
            ClientFacade.getInstance().updateObservable(state);
        }
        else {
            handleException(response.getException());
        }
    }

    public static void handleResponse(LogoutResponse response) {
        if (response == null) {
            handleException(new Exception("The Server could not be reached"));
        }
        else if (response.getException() == null) {
            ClientStateChange.ClientState stateVal;
            stateVal = ClientStateChange.ClientState.login;
            ClientStateChange state = new ClientStateChange(stateVal);
            ClientFacade.getInstance().updateObservable(state);
        }
        else {
            handleException(response.getException());
        }
    }

    public static void handleResponse(StartGameResponse response) {
        if (response == null) {
            handleException(new Exception("The Server could not be reached"));
        }
        else if (response.getException() == null) {
            ClientFacade.getInstance().startGame(response.getGame(), response.getPlayerHand(),
                    response.getDestCardOptions());
        }
        else {
            handleException(response.getException());
        }
    }

    public static void handleResponse(LeaveLobbyResponse response) {
        if (response == null) {
            handleException(new Exception("The Server could not be reached"));
        }
        else if (response.getException() == null) {
            ClientStateChange.ClientState stateVal;
            stateVal = ClientStateChange.ClientState.lobbylist;
            ClientStateChange state = new ClientStateChange(stateVal);
            ClientFacade.getInstance().setCurrentLobby(null);
            ClientFacade.getInstance().updateLobbyList(response.getLobbyList());
            ClientFacade.getInstance().updateObservable(state);
        }
        else {
            handleException(response.getException());
        }
    }

    public static void handleResponse(Response response) {
        if (response == null) {
            handleException(new Exception("The Server could not be reached"));
        }
        else if (response.getException() == null) {
            //Do nothing
        } else {
            handleException(response.getException());
        }
    }

    public static void handleResponse(TrainCardResponse response) {
        if (response == null) {
            handleException(new Exception("The Server could not be reached"));
        }
        else if (response.getException() == null) {
            ClientFacade.getInstance().getLocalPlayer().addTrainCardToHand(response.getCard());
            ClientFacade.getInstance().addPlayerTrainCard();
            ClientModelUpdate message = new ClientModelUpdate(
                    ClientModelUpdate.ModelUpdate.playerTrainHandUpdated);
            ClientFacade.getInstance().updateObservable(message);
        }
        else {
            handleException(response.getException());
        }
    }

    public static void handleResponse(ClaimRouteResponse response) {
        if (response == null) {
            handleException(new Exception("The Server could not be reached"));
        }
        else if (response.getException() == null) {
            ClientFacade.getInstance().removeUsedCardsFromPlayerHand(response.getRemoveCards());
            ClientModelUpdate message = new ClientModelUpdate(
                    ClientModelUpdate.ModelUpdate.playerTrainHandUpdated);
            ClientFacade.getInstance().updateObservable(message);
        }
        else {
            handleException(response.getException());
        }
    }

    public static void handleResponse(DestinationCardResponse response, boolean selectedCards) {
        if (response == null) {
            handleException(new Exception("The Server could not be reached"));
        }
        else if (response.getException() == null) {
            List<DestinationCard> cards = response.getCards();
            ClientModelUpdate message;
            if (! selectedCards) {
                ChoiceDestinationCards choice = new ChoiceDestinationCards();
                choice.setDestinationCards(cards);
                ClientFacade.getInstance().setDestinationCardOptions(choice);
                message = new ClientModelUpdate(
                        ClientModelUpdate.ModelUpdate.destCardOptionsUpdated);
            }
            else {
                Player localPlayer = ClientFacade.getInstance().getLocalPlayer();
                for (DestinationCard card : cards)
                    localPlayer.addDestinationCardToHand(card);
                ClientFacade.getInstance().setDestinationCardOptions(null);
                ClientFacade.getInstance().addPlayerDestinationCards(localPlayer.getName(), cards.size());
                message = new ClientModelUpdate(
                        ClientModelUpdate.ModelUpdate.playerDestHandUpdated);
            }
            ClientFacade.getInstance().updateObservable(message);
        }
        else {
            handleException(response.getException());
        }
    }
}
