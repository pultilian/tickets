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
  public AddGuestResponse addGuest(String lobbyID, String authToken);

  //Game Actions
  public PlayerTurnResponse takeTurn(String playerID, String authToken);
  public AddToChatResponse addToChat(String message, String authToken);

  //Poller response object
  public ClientUpdate updateClient(String lastReceivedCommandID, String authToken);
}
