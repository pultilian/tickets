
package tickets.server.model.game;

import java.util.List;

import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Route;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player has drawn three destination cards
//	 and must now choose whether to discard two, one or zero of them.
//
class PickingDestCardsState extends PlayerTurnState {
	PickingDestCardsState(ServerPlayer player) {
		player.super();
	}

	@Override
	TrainCard state_drawTrainCard() throws Exception {
		throw new Exception("You must decide which destination cards to keep");
	}

	@Override
	TrainCard state_drawFaceUpCard(int position) throws Exception {
		throw new Exception("You must decide which destination cards to keep");
	}

	@Override
	void state_claimRoute(Route route) throws Exception {
		throw new Exception("You must decide which destination cards to keep");
	}

	@Override
	List<DestinationCard> state_drawDestinationCards() throws Exception {
		throw new Exception("You must decide which destination cards to keep");
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) throws Exception {
		List<DestinationCard> cards = getTempDestinationCards_fromPlayer();
		int removeIndex = -1;
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).equals(discard)) {
				removeIndex = i;
			}
		}
		cards.remove(removeIndex);
		if (cards.size() == 1) {
			addDestinationCardToHand_fromPlayer(cards.get(0));
			changeStateTo(ServerPlayer.States.WAIT_FOR_TURN);
		}
		return;
	}

	@Override
	void state_endTurn() throws Exception {
		List<DestinationCard> cards = getTempDestinationCards_fromPlayer();
		for (DestinationCard c : cards) {
			addDestinationCardToHand_fromPlayer(c);
		}
		changeStateTo(ServerPlayer.States.WAIT_FOR_TURN);
		return;
	}

	@Override
	void state_addToChat(String msg) {
		addToChat_fromPlayer(msg);
		return;
	}
}