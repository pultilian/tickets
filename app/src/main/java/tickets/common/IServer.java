package tickets.common;

import tickets.common.response.*;

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
    //To remove
  public PlayerTurnResponse takeTurn(String playerID, String authToken);
  public Response chooseDestinationCards(DestinationCard toDiscard, String authToken);

    //Player in-game actions
  public TrainCardResponse drawTrainCard(String authToken);
  public TrainCardResponse drawFaceUpCard(int position, String authToken);
  public Response claimRoute(Route route, String authToken);
  public DestinationCardResponse drawDestinationCards(String authToken);
  public Response discardDestinationCard(DestinationCard discard, String authToken);
  public Response endTurn(String authToken);

  public AddToChatResponse addToChat(String message, String authToken);
  

  //TODO:
  //  implement player in-game actions on ServerFacade
  //  implement player in-game actions on ServerProxy

  //Poller response object
  public ClientUpdate updateClient(String lastReceivedCommandID, String authToken);
}
