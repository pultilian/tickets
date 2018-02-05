package common;

import common.response.*;

public interface IServer {

    LoginResponse login(String username, String password);

    LoginResponse register(String username, String password);

    JoinLobbyResponse joinLobby(String lobbyID);

    LogoutResponse logout();

    StartGameResponse startGame(String lobbyID);

    LeaveLobbyResponse leaveLobby(String lobbyID);

    AddGuestResponse addGuest(String lobbyID);

    PlayerTurnResponse takeTurn(String playerID);
}
