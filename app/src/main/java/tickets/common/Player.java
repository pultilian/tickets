package tickets.common;

import java.util.List;
import java.util.Map;

public class Player {
    private PlayerInfo info;

    private String playerId;
    private String associatedAuthToken;
    private HandDestinationCard playerDestinationCards;
    private HandTrainCard playerResourceCards;
    private ChoiceDestinationCards destinationCardOptions;

    public Player(Player copy) {
        this.info = copy.info;
        this.playerId = copy.playerId;
        this.associatedAuthToken = copy.associatedAuthToken;
        this.playerDestinationCards = copy.playerDestinationCards;
        this.playerResourceCards = copy.playerResourceCards;
        this.destinationCardOptions = copy.destinationCardOptions;
    }

    public Player(String playerId, String associatedAuthToken) {
        info = new PlayerInfo();

        this.playerId = playerId;
        this.associatedAuthToken = associatedAuthToken;
        playerDestinationCards = new HandDestinationCard();
        playerResourceCards = new HandTrainCard();
        destinationCardOptions = null;
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public String getPlayerId(){ return playerId; }

    public String getAssociatedAuthToken(){ return associatedAuthToken; }

    public String getName() {
        return info.getName();
    }

    public void setName(String name) {
        info.setName(name);
    }

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

    public void removeTrainCard(RouteColors cardColor) {
        playerResourceCards.removeCard(cardColor);
    }

    public HandDestinationCard getHandDestinationCards() {
        return playerDestinationCards;
    }

    public void addDestinationCardToHand(DestinationCard card){
        playerDestinationCards.addCard(card);
    }

    public void setDestinationCardOptions(ChoiceDestinationCards cards) {
        destinationCardOptions = cards;
    }

    public List<DestinationCard> getDestinationCardOptions(){
        return destinationCardOptions.getDestinationCards();
    }

    public void setTrainCardHand(HandTrainCard playerHand) {
        playerResourceCards = playerHand;
    }

    public void removeUsedTrainCards(Map<RouteColors, Integer> removeCards) {
        for (RouteColors color : removeCards.keySet()) {
            playerResourceCards.removeCards(color, removeCards.get(color));
        }
    }
}
