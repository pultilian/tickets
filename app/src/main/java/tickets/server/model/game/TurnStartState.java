
package tickets.server.model.game;

import java.util.ArrayList;
import java.util.List;

import tickets.common.DestinationCard;
import tickets.common.Route;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player's turn has begun. They may choose
//	 any viable action - if that action is a multi-step process,
//	 then their state changes to reflect the decisions then
//	 available to them. Otherwise, their turn ends.
//
class TurnStartState extends PlayerTurnState {
	TurnStartState(ServerPlayer player) {
		player.super();
	}

	@Override
	TrainCard state_drawTrainCard() throws Exception {
		TrainCard card = drawTrainCard_fromPlayer();
		if (card == null) {
			throw new Exception("There are no train cards in the deck to be drawn");
		}
		addTrainCardToHand_fromPlayer(card);
		changeStateTo(ServerPlayer.States.DRAWING_TRAIN_CARDS);
		return card;
	}

	@Override
	TrainCard state_drawFaceUpCard(int position) throws Exception {
		TrainCard card = drawFaceUpCard_fromPlayer(position);
		if (card == null) {
			throw new Exception("There are no face up train cards to be drawn");
		}
		addTrainCardToHand_fromPlayer(card);
		if (card.getColor() == RouteColors.Wild) {
			changeStateTo(ServerPlayer.States.WAIT_FOR_TURN);
		}
		else {
			changeStateTo(ServerPlayer.States.DRAWING_TRAIN_CARDS);
		}
		return card;
	}

	@Override
	void state_claimRoute(Route route) throws Exception {
		//---
		// Check that the player has enough Train cards of the route's color
		// If not:
		//	 Check that the player has enough wild cards and colored train cards
		//	    If not:
		//         the player cannot claim this route
		// 
		// Discard train cards (of the route's color or wild) equal to the route's length
		// Claim the route on the map
		// Lose train tokens equal to the route's length
		// Gain points equal to the route's value
		//---
		throw new Exception("claiming routes has not been implemented.");
	}

	@Override
	List<DestinationCard> state_drawDestinationCards() throws Exception {
		//draw three destination cards
		DestinationCard card1 = drawDestinationCard_fromPlayer();
		DestinationCard card2 = drawDestinationCard_fromPlayer();
		DestinationCard card3 = drawDestinationCard_fromPlayer();

		if (card1 == null || card2 == null || card3 == null) {
			throw new Exception("There are not enough destination cards left in the deck.");
		}

		List<DestinationCard> cards = new ArrayList<>();
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);

		changeStateTo(ServerPlayer.States.PICKING_DEST_CARDS);
		return cards;
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) throws Exception {
		throw new Exception("You cannot discard destination cards you've already picked");
	}

	@Override
	void state_endTurn() throws Exception {
		throw new Exception("You must choose an action to take on your turn");
	}

	@Override
	void state_addToChat(String msg) {
		addToChat_fromPlayer(msg);
		return;
	}
}