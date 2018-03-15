package tickets.common.response;

import java.util.List;

import tickets.common.Lobby;

public class LoginResponse extends Response {

    private String message;
    private String authToken;
    private List<Lobby> lobbyList;

    public LoginResponse(Exception exception){
        super(exception);
    }

    public LoginResponse(String message, String authToken, List<Lobby> lobbyList){
        this.message = message;
        this.authToken = authToken;
        this.lobbyList = lobbyList;
    }

    public String getMessage(){ return message; }

    public String getAuthToken(){ return authToken; }

    public List<Lobby> getLobbyList() { return lobbyList; }
}
