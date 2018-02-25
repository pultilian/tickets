package tickets.common;

public class Player {

    private String playerId;
    private String associatedAuthToken;
    private Faction playerFaction;

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
