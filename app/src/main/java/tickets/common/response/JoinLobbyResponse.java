package tickets.common.response;

import tickets.common.Lobby;
import tickets.common.Player;

public class JoinLobbyResponse extends Response {

    private Lobby lobby;
    private Player player;
    public JoinLobbyResponse(Exception exception) {
		super(exception);
	}

    public JoinLobbyResponse(Lobby lobby, Player player){
        this.lobby = lobby;
        this.player = player;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public Player getPlayer() {
        return player;
    }
}
