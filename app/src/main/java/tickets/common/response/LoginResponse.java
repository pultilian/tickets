package tickets.common.response;

import java.util.List;

import tickets.common.Lobby;
import tickets.common.Game;

public class LoginResponse extends Response {

    private String message;
    private String authToken;
    private List<Lobby> lobbyList;
    private List<Lobby> currentLobbies;
    private List<Game> currentGames;

    public LoginResponse(Exception exception){
        super(exception);
    }

    public LoginResponse(String message, String authToken, List<Lobby> lobbyList){
        this.message = message;
        this.authToken = authToken;
        this.lobbyList = lobbyList;
    }

    public void setCurrentLobbies(List<Lobby> currentLobbies) {
        this.currentLobbies = currentLobbies;
    }

    public void setCurrentGames(List<Game> currentGames) {
        this.currentGames = currentGames;
    }

    public String getMessage(){
        return message;
    }

    public String getAuthToken(){
        return authToken;
    }

    public List<Lobby> getLobbyList() {
        return lobbyList;
    }

    public List<Lobby> getCurrentLobbies() {
        return currentLobbies;
    }

    public List<Game> getCurrentGames() {
        return currentGames;
    }
}
