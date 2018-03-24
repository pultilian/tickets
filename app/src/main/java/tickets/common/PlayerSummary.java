package tickets.common;

/**
 * Created by Pultilian on 3/23/2018.
 */

public class PlayerSummary {
    private String playerName;
    private String faction;
    private int shipsLeft;
    private int shipPoints;
    private int successDestPoints;

    // keep this a positive number.
    private int failDestPoints;
    private boolean longestRoute;

    public PlayerSummary(PlayerInfo playerInfo, int successDestPoints, int failDestPoints, boolean longestRoute) {
        this.playerName = playerInfo.getName();
        this.faction = playerInfo.getFaction().getName();
        this.shipsLeft = playerInfo.getShipsLeft();
        this.shipPoints = playerInfo.getScore();
        this.successDestPoints = successDestPoints;
        this.failDestPoints = failDestPoints;
        this.longestRoute = longestRoute;
    }
}
