package tickets.common;

import tickets.common.response.*;
import tickets.common.response.data.*;

public interface IServer {
    //Client actions
    public LoginResponse login(UserData userData);
    public LoginResponse register(UserData userData);
    public JoinLobbyResponse joinLobby(String lobbyID, String authToken);
    public JoinLobbyResponse createLobby(Lobby lobby, String authToken);
    public LogoutResponse logout(String authToken);
    public StartGameResponse startGame(String lobbyID, String authToken);
    public LeaveLobbyResponse leaveLobby(String lobbyID, String authToken);
    public AddGuestResponse addGuest(String lobbyID, String authToken);
    public PlayerTurnResponse takeTurn(String playerID, String authToken);

    //Data fetching
    public LobbyListData getAllLobbies(String authToken);
    public LobbyData getLobbyData(String lobbyID, String authToken);
    public ClientUpdate getClientUpdate(String authToken);
}
