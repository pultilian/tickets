package common;

import common.response.*;

public interface IServer {

    public LoginResponse login(String username, String password);

    public LoginResponse register(String username, String password);

    public JoinLobbyResponse joinLobby(String lobbyID);

    public LogoutResponse logout();

    public StartGameResponse startGame(String lobbyID);

    public LeaveLobbyResponse leaveLobby(String lobbyID);

    public AddGuestResponse addGuest(String lobbyID);

    public PlayerTurnResponse takeTurn(String playerID);
}
