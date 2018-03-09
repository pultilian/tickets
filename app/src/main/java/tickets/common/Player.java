package tickets.common;

import tickets.common.HandTrainCard;
import tickets.common.HandDestinationCard;

public class Player {
    private PlayerInfo info;

    private String playerId;
    private String associatedAuthToken;
    private HandDestinationCard playerDestinationCards;
    private HandTrainCard playerResourceCards;

    public Player(String playerId, String associatedAuthToken) {
        info = new PlayerInfo();

        this.playerId = playerId;
        this.associatedAuthToken = associatedAuthToken;
        playerDestinationCards = new HandDestinationCard();
        playerResourceCards = new HandTrainCard();
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

    public HandTrainCard getHandTrainCards() {
        return playerResourceCards;
    }

    public void addTrainCardToHand(TrainCard card){
        playerResourceCards.addCard(card);
    }

    public HandDestinationCard getHandDestinationCards() {
        return playerDestinationCards;
    }

    public void addDestinationCardToHand(DestinationCard card){
        playerDestinationCards.addCard(card);
    }

}
