package common;

public class Player {

    private String playerId;

    public Player(String playerId){
        this.playerId = playerId;
    }

    public String getPlayerId(){ return playerId; }

    public void setPlayerId(String playerId){ this.playerId = playerId; }
}
