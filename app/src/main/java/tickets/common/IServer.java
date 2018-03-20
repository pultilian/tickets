package tickets.common;

import java.util.List;

import tickets.common.response.AddToChatResponse;
import tickets.common.response.ClientUpdate;
import tickets.common.response.DestinationCardResponse;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.response.LeaveLobbyResponse;
import tickets.common.response.LoginResponse;
import tickets.common.response.LogoutResponse;
import tickets.common.response.Response;
import tickets.common.response.StartGameResponse;
import tickets.common.response.TrainCardResponse;

public interface IServer {
  //Lobby List Actions
  public LoginResponse login(UserData userData);
  public LoginResponse register(UserData userData);
  public JoinLobbyResponse joinLobby(String lobbyID, String authToken);
  public JoinLobbyResponse createLobby(Lobby lobby, String authToken);
  public LogoutResponse logout(String authToken);

  //Lobby Actions
  public StartGameResponse startGame(String lobbyID, String authToken);
  public LeaveLobbyResponse leaveLobby(String lobbyID, String authToken);

  //Game Actions
  public TrainCardResponse drawTrainCard(String authToken);
  public TrainCardResponse drawFaceUpCard(int position, String authToken);
  public Response claimRoute(Route route, List<TrainCard> cards, String authToken);
  public DestinationCardResponse drawDestinationCards(String authToken);
  public Response discardDestinationCard(DestinationCard discard, String authToken);
  public Response endTurn(String authToken);
  public AddToChatResponse addToChat(String message, String authToken);

  //Poller response object
  public ClientUpdate updateClient(String lastReceivedCommandID, String authToken);
}
