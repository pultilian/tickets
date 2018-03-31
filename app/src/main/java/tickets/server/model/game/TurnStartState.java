
package tickets.server.model.game;

import java.util.List;

import tickets.common.ChoiceDestinationCards;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Route;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player's turn has begun and they may choose any action.
class TurnStartState extends PlayerTurnState {

    // Singleton pattern
    private static TurnStartState INSTANCE;

    static TurnStartState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TurnStartState();
        }
        return INSTANCE;
    }

    private TurnStartState(){}

    @Override
    String drawTrainCard(TrainCard card, ServerPlayer player) {
        player.addTrainCardToHand(card);
        player.changeState(States.DREW_ONE_TRAIN_CARD);
        return null;
    }

    @Override
    String drawFaceUpCard(TrainCard card, ServerPlayer player) {
        player.addTrainCardToHand(card);
        if (card.getColor() == RouteColors.Wild) {
            player.changeState(States.NOT_MY_TURN);
            return ServerPlayer.END_TURN;
        }
        else {
            player.changeState(States.DREW_ONE_TRAIN_CARD);
        }
        return null;
    }

    @Override
    String claimRoute(Route route, List<TrainCard> cards, ServerPlayer player) {
        // Check enough ships left
        if (player.getInfo().getShipsLeft() < route.getLength()) return "Not enough ships left.";

        // Get color of route to be claimed from train card color
        RouteColors color = null;
        for (TrainCard card : cards) {
            if (card.getColor() != RouteColors.Wild) {
                if (color == null) color = card.getColor();
                else if (color != card.getColor()) return "Cards must all be the same color or wild.";
            }
        }

        // Check correct number of cards
        if (route.getLength() != cards.size()) return "Incorrect number of cards.";

        // Attempt to claim route
        if (route.claim(color, player.getPlayerFaction().getColor())) {
            for (TrainCard card : cards) {
                player.removeTrainCard(card.getColor());
            }
            player.getInfo().addToScore(route.getPointValue());
            player.getInfo().useShips(route.getLength());
            player.addRouteToMap(route);
            player.changeState(States.NOT_MY_TURN);
            if (player.getInfo().getShipsLeft() < 3) return ServerPlayer.LAST_ROUND;
            else return null;
        }
        else return "You cannot claim this route.";
    }

    @Override
    String drawDestinationCards(List<DestinationCard> cards, ServerPlayer player) {
        ChoiceDestinationCards choices = new ChoiceDestinationCards();
        choices.setDestinationCards(cards);
        player.setDestinationCardOptions(choices);
        player.changeState(States.DREW_DESTINATION_CARDS);
        return null;
    }

    @Override
    String discardDestinationCard(List<DestinationCard> card, ServerPlayer player) {
        return "You must draw destination cards before discarding them.";
    }

    @Override
    String endTurn(ServerPlayer player) {
        return "You must perform an action on your turn.";
    }
}