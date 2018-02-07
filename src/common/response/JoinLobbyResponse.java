package common.response;

import java.util.List;

public class JoinLobbyResponse extends Response {

    private String lobbyID;
    private List<String> lobbyHistory;

    public JoinLobbyResponse(Exception exception){
        super(exception);
    }

    public JoinLobbyResponse(String lobbyID, List<String> lobbyHistory){
        this.lobbyID = lobbyID;
        this.lobbyHistory = lobbyHistory;
    }

    public String getLobbyID(){ return lobbyID; }

    public List<String> getLobbyHistory(){ return lobbyHistory; }
}
