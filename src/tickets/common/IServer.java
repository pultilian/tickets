package tickets.common;

import common.response.*;

public interface IServer {

    public LoginResponse login(UserData userData);

    public LoginResponse register(UserData userData);

    public JoinLobbyResponse joinLobby(String lobbyID);
    
    public JoinLobbyResponse createLobby(Lobby lobby);

    public LogoutResponse logout();

    public StartGameResponse startGame(String lobbyID);

    public LeaveLobbyResponse leaveLobby(String lobbyID);

    public AddGuestResponse addGuest(String lobbyID);

    public PlayerTurnResponse takeTurn(String playerID);
}
