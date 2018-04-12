package tickets.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private PlayerInfo info;

    private String associatedAuthToken;
    private HandDestinationCard playerDestinationCards;
    private HandTrainCard playerResourceCards;
    private ChoiceDestinationCards destinationCardOptions;

    public Player(Player copy) {
        this.info = copy.info;
        this.associatedAuthToken = copy.associatedAuthToken;
        this.playerDestinationCards = copy.playerDestinationCards;
        this.playerResourceCards = copy.playerResourceCards;
        this.destinationCardOptions = copy.destinationCardOptions;
    }

    public Player(String associatedAuthToken) {
        info = new PlayerInfo();

        this.associatedAuthToken = associatedAuthToken;
        playerDestinationCards = new HandDestinationCard();
        playerResourceCards = new HandTrainCard();
        destinationCardOptions = null;
    }

    public PlayerInfo getInfo() {
        return info;
    }

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
        info.addTrainCard();
    }

    public void removeTrainCard(RouteColors cardColor) {
        playerResourceCards.removeCard(cardColor);
    }

    public HandDestinationCard getHandDestinationCards() {
        return playerDestinationCards;
    }

    public void addDestinationCardToHand(DestinationCard card){
        playerDestinationCards.addCard(card);
        info.addDestinationCards(1);
    }

    public void setDestinationCardOptions(ChoiceDestinationCards cards) {
        destinationCardOptions = cards;
    }

    public List<DestinationCard> getDestinationCardOptions(){
        if (destinationCardOptions == null)
            return null;
        return destinationCardOptions.getDestinationCards();
    }

    public void setTrainCardHand(HandTrainCard playerHand) {
        playerResourceCards = playerHand;
    }

    public void removeUsedTrainCards(List<TrainCard> removeCards) {
        for (TrainCard card : removeCards) {
            RouteColors color = card.getColor();
            playerResourceCards.removeCards(color, 1);
        }
    }

    public List<String> getPossibleColorsForRoute(Route route) {
        List<String> result = new ArrayList<>();

        // For wild routes, check all colors
        if (route.getFirstColor() == RouteColors.Gray) {
            int numWild = playerResourceCards.getCountForColor(RouteColors.Wild);
            Map<RouteColors, List<TrainCard>> cards = playerResourceCards.getAllCards();
            for (Map.Entry<RouteColors, List<TrainCard>> entry : cards.entrySet()) {
                if (entry.getKey() == RouteColors.Wild) continue;
                if (entry.getValue().size() > 0 && (entry.getValue().size() + numWild) >= route.getLength()) {
                    result.add(entry.getKey().toString());
                }
            }
        }

        // For all other routes, check both colors
        else {
            RouteColors color = route.getFirstColor();
            int numWild = playerResourceCards.getCountForColor(RouteColors.Wild);
            if (playerResourceCards.getCountForColor(color) + numWild >= route.getLength()) {
                result.add(color.toString());
            }
            color = route.getSecondColor();
            if (playerResourceCards.getCountForColor(color) + numWild >= route.getLength()) {
                result.add(color.toString());
            }
        }
        return result;
    }

    public List<TrainCard> getCards(int length, String color) {
        RouteColors selectedColor = null;
        for (RouteColors routeColor : RouteColors.values()) {
            if (routeColor.toString().equals(color)) selectedColor = routeColor;
        }
        if (selectedColor == null) return null;
        return playerResourceCards.getCards(length, selectedColor);
    }
}
