
package tickets.server.model.game;

import java.util.List;

import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Route;

// Part of the state pattern describing a player's overall turn
//
// At this state, the player has already drawn one train card and must draw another train card,
// either from the deck or a non-wild face-up card.
class DrewOneTrainCardState extends PlayerTurnState {

	// Singleton pattern
	private static DrewOneTrainCardState INSTANCE;

	static DrewOneTrainCardState getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DrewOneTrainCardState() {
			};
		}
		return INSTANCE;
	}

	private DrewOneTrainCardState(){}

	@Override
	String drawTrainCard(TrainCard card, ServerPlayer player) {
		player.addTrainCardToHand(card);
		player.changeState(States.NOT_MY_TURN);
		return ServerPlayer.END_TURN;
	}

	@Override
	String drawFaceUpCard(TrainCard card, ServerPlayer player) {
	    if (card.getColor() == RouteColors.Wild) {
	        return "You may not draw a wild face-up card.";
        }
        else {
	        player.addTrainCardToHand(card);
	        player.changeState(States.NOT_MY_TURN);
	        return ServerPlayer.END_TURN;
        }
	}

	@Override
	String claimRoute(Route route, List<TrainCard> cards, ServerPlayer player) {
		return "You must draw another resource card.";
	}

	@Override
	String drawDestinationCards(List<DestinationCard> cards, ServerPlayer player) {
		return "You must draw another resource card.";
	}

	@Override
	String discardDestinationCard(List<DestinationCard> card, ServerPlayer player) {
		return "You must draw another resource card.";
	}

	@Override
	String endTurn(ServerPlayer player) {
		return "You must draw another resource card.";
	}
}