package tickets.common.response;

import tickets.common.Lobby;

import java.util.List;

public class LeaveLobbyResponse extends Response {

    private String message;
    private List<Lobby> lobbyList;

    public LeaveLobbyResponse(Exception exception){
        super(exception);
    }

    public LeaveLobbyResponse(String message, List<Lobby> lobbyList){
        this.message = message;
        this.lobbyList = lobbyList;
    }

    public String getMessage(){ return message; }

    public List<Lobby> getLobbyList() { return lobbyList; }
}
