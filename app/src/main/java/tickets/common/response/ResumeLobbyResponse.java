package tickets.common.response;

import tickets.common.Lobby;
import tickets.common.Player;

public class ResumeLobbyResponse extends Response {

    private Lobby lobby;
    private Player player;
    private String authToken;

    public ResumeLobbyResponse(Exception exception) {
        super(exception);
    }

    public ResumeLobbyResponse(Lobby lobby, Player player, String authToken) {
        this.lobby = lobby;
        this.player = player;
        this.authToken = authToken;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public Player getPlayer() {
        return player;
    }

    public String getAuthToken() {
        return authToken;
    }
}
