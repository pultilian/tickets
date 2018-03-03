package tickets.common;

public class Player {

    private String playerId;
    private String associatedAuthToken;
    private Faction playerFaction;
    private String name;
    private int points;
    private int shipsLeft;
    private HandDestinationCard playerDestinationCards;
    private HandTrainCard playerResourceCards;

    public Player(String playerId, String associatedAuthToken){
        this.playerId = playerId;
        this.associatedAuthToken = associatedAuthToken;
        playerFaction = null;
    }

    public String getPlayerId(){ return playerId; }

    public String getAssociatedAuthToken(){ return associatedAuthToken; }

    public Faction getPlayerFaction() {
        return playerFaction;
    }

    public void setPlayerFaction(Faction playerFaction) {
        this.playerFaction = playerFaction;
    }

}
