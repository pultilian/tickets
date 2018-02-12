package tickets.common;

public class Player {

    private String playerId;
    private String associatedAuthToken;

    public Player(String playerId, String associatedAuthToken){
        this.playerId = playerId;
        this.associatedAuthToken = associatedAuthToken;
    }

    public String getPlayerId(){ return playerId; }

    public String getAssociatedAuthToken(){ return associatedAuthToken; }
}
