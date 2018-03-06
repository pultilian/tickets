package tickets.common;

public class Player {
    private PlayerInfo info;
    // general information the player can present publicly
    //
    // private Faction playerFaction; - in info
    // private String name; - in info
    // private int points; - in info
    // private int shipsLeft; - in info

    private String playerId;
    private String associatedAuthToken;
    private HandDestinationCard playerDestinationCards;
    private HandTrainCard playerResourceCards;

    public Player(String playerId, String associatedAuthToken) {
        info = new PlayerInfo();

        this.playerId = playerId;
        this.associatedAuthToken = associatedAuthToken;
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public String getPlayerId(){ return playerId; }

    public String getAssociatedAuthToken(){ return associatedAuthToken; }

    public Faction getPlayerFaction() {
        return info.getFaction();
    }

    public void setPlayerFaction(Faction playerFaction) {
        info.setFaction(playerFaction);
        return;
    }

}
