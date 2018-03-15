
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
        }
        else {
            player.changeState(States.DREW_ONE_TRAIN_CARD);
        }
        return null;
    }

    @Override
    String claimRoute(Route route, ServerPlayer player) {
        // If route can be claimed, claim it. Otherwise, return an error message.
        // TODO: Implement this
        player.changeState(States.NOT_MY_TURN);
        return null;
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
    String discardDestinationCard(DestinationCard card, ServerPlayer player) {
        return "You must draw destination cards before discarding them.";
    }

    @Override
    String endTurn(ServerPlayer player) {
        return "You must perform an action on your turn.";
    }
}