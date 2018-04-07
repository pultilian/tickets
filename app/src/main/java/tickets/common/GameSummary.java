package tickets.common;

import java.util.List;

public class GameSummary {
    private List<PlayerSummary> playerSummaries;

    public GameSummary(List<PlayerSummary> playerSummaries) {
        this.playerSummaries = playerSummaries;
    }

    public List<PlayerSummary> getPlayerSummaries() {
        return playerSummaries;
    }
}
