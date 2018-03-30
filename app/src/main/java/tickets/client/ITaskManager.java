package tickets.client;

import tickets.common.DestinationCard;
import tickets.common.Lobby;
import tickets.common.Route;
import tickets.common.TrainCardWrapper;
import tickets.common.UserData;

public interface ITaskManager {

    void register(UserData userData);

    void login(UserData userData);

    void joinLobby(String id);

    void createLobby(Lobby lobby);

    void logout();

    void startGame(String lobbyID);

    void leaveLobby(String lobbyID);

    void addToChat(String message);

    void drawTrainCard();

    void drawFaceUpCard(int position);

    void claimRoute(Route route, TrainCardWrapper cards);

    void drawDestinationCard();

    void discardDestinationCard(DestinationCard discard);
}
