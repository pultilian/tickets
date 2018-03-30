package tickets.common;

/**
 * Created by Pultilian on 3/23/2018.
 */

public class PlayerSummary {
    public String getPlayerName() {
        return playerName;
    }

    public String getFaction() {
        return faction;
    }

    public int getShipsLeft() {
        return shipsLeft;
    }

    public int getShipPoints() {
        return shipPoints;
    }

    public int getSuccessDestPoints() {
        return successDestPoints;
    }

    public int getFailDestPoints() {
        return failDestPoints;
    }

    public boolean isLongestRoute() {
        return longestRoute;
    }

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
