
package tickets.server.model.game;

import java.util.ArrayList;
import java.util.List;

import tickets.common.ChoiceDestinationCards;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Route;

// Part of the state pattern describing a player's overall turn
//
// At this state, the player has drawn three destination cards
//	 and must now choose whether to discard two, one or zero of them.
//
class DrewDestCardsState extends PlayerTurnState {

	// Singleton pattern
	private static DrewDestCardsState INSTANCE;

	static DrewDestCardsState getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DrewDestCardsState();
		}
		return INSTANCE;
	}

	private boolean turn0;
	private DrewDestCardsState() {
	    turn0 = true;
    }

	@Override
	String drawTrainCard(TrainCard card, ServerPlayer player) {
		return "You must choose which destination cards (if any) to discard.";
	}

	@Override
	String drawFaceUpCard(TrainCard card, ServerPlayer player) {
		return "You must choose which destination cards (if any) to discard.";
	}

	@Override
	String claimRoute(Route route, List<TrainCard> cards, ServerPlayer player) {
		return "You must choose which destination cards (if any) to discard.";
	}

	@Override
	String drawDestinationCards(List<DestinationCard> cards, ServerPlayer player) {
		return "You must choose which destination cards (if any) to discard.";
	}

	@Override
	String discardDestinationCard(List<DestinationCard> cards, ServerPlayer player) {
		if (turn0 && cards.size() > 1)
		    return "You may only discard one card during setup";
		else if (!turn0 && cards.size() > 2)
		    return "You may only discard up to two cards";


		List<DestinationCard> options = player.getDestinationCardOptions();
		List<DestinationCard> toDiscard = new ArrayList<>();

		// Remove all discarded cards
		for (DestinationCard card : cards) {
			if (card != null) {
				for (DestinationCard option : options) {
					if (card.equals(option)) toDiscard.add(option);
				}
			}
		}
		options.removeAll(toDiscard);

		// Set player's destination card options to the kept cards
		ChoiceDestinationCards keptCards = new ChoiceDestinationCards();
		keptCards.setDestinationCards(options);
		player.setDestinationCardOptions(keptCards);

		// Add all kept cards to the player's hand
		for (DestinationCard card : player.getDestinationCardOptions()) {
			player.addDestinationCardToHand(card);
		}
        turn0 = false;
        player.changeState(States.NOT_MY_TURN);
		return null;
	}

	@Override
	String endTurn(ServerPlayer player) {
		return "You must choose which destination cards (if any) to discard.";
	}
}