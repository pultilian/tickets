package tickets.common.response;

import tickets.common.Game;
import tickets.common.Player;

public class ResumeGameResponse extends Response {

    private Game game;
    private Player player;
    private String authToken;

    public ResumeGameResponse(Exception exception) {
        super(exception);
    }

    public ResumeGameResponse(Game game, Player player, String authToken) {
        this.game = game;
        this.player = player;
        this.authToken = authToken;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public String getAuthToken() {
        return authToken;
    }
}
