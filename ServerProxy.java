import com.sun.deploy.nativesandbox.comm.Response;

/**
 * Created by Pultilian on 2/1/2018.
 */
public class ServerProxy {
    private static ServerProxy INSTANCE = null;
    private ClientModelRoot clientModelRoot;

    public static ServerProxy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerProxy();
        }
        return (INSTANCE);
    }

    public Response login(UserData data){
        return null;
    }

    public Response register(UserData data){
        return null;
    }

    public Response login(UserData data){
        return null;
    }

    public Response joinLobby(int lobbyID){
        return null;
    }


    public Response login(UserData data){
        return null;
    }

    public Response createLobby(LobbyData data){
        return null;
    }


    public Response startGame(int lobbyID){
        return null;
    }

    public Response logout(int userID){
        return null;
    }

    public Response leaveLobby(int userID){
        return null;
    }

    public Response addGuest(int lobbyID){
        return null;
    }

    public Response takeTurn(int playerID){
        return null;
    }

}
