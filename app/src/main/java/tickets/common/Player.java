package tickets.common;

import tickets.common.HandTrainCard;
import tickets.common.HandDestinationCard;

public class Player {

    private String playerId;
    private String associatedAuthToken;
    private Faction playerFaction;

    private HandTrainCard trainCards;
    private HandDestinationCard destinationCards;
    private int score;

    public Player(String playerId, String associatedAuthToken) {
        this.playerId = playerId;
        this.associatedAuthToken = associatedAuthToken;
        playerFaction = null;

        trainCards = new HandTrainCard();
    }

    public String getPlayerId(){ return playerId; }

    public String getAssociatedAuthToken(){ return associatedAuthToken; }

    public Faction getPlayerFaction() {
        return playerFaction;
    }

    public void setPlayerFaction(Faction playerFaction) {
        this.playerFaction = playerFaction;
    }

    public HandTrainCard getHandTrainCards() {
        return trainCards;
    }

    public HandDestinationCard getHandDestinationCards() {
        return destinationCards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
