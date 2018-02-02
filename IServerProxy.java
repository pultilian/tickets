/**
 * Created by Pultilian on 2/1/2018.
 */
public interface IServerProxy {
    static ServerProxy INSTANCE = null;
    ClientModelRoot clientModelRoot;

    public ServerProxy getInstance();
    public Command login(UserData data);
    public Command register(UserData data);
    public Command login(UserData data);
    public Command joinLobby(int lobbyID);
    public Command login(UserData data);
    public Command createLobby(LobbyData data);
    public Command startGame(int lobbyID);
    public Command logout(int userID);
    public Command leaveLobby(int userID);
    public Command addGuest(int lobbyID);
    public Command takeTurn(int playerID);
}
