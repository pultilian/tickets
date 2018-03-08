
package tickets.server.model.game;

import tickets.common.DestinationCard;
import tickets.common.Route;

import tickets.server.model.game.ServerPlayer;
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
	void state_drawTrainCard() {
		//---
		// If the Train Card deck is empty:
		//	 throw new Exception("There are no train cards in the deck to be drawn");
		//
		// Draw the top card of the Train card deck
		// Add it to the player's hand
		// Go to DrawingTrainCardsState
		//---
	}

	@Override
	void state_drawFaceUpCard(int position) {
		//---
		// If the Train card deck is empty:
		//	 throw new Exception("There are no face up train cards to be drawn");
		//
		// Draw the face up card at the specified position
		// Add it to the player's hand
		// If the card drawn is wild:
		//	 end the turn
		// Otherwise:
		//	 Go to DrawingTrainCardsState
		//---
	}

	@Override
	void state_claimRoute(Route route) {
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
	}

	@Override
	void state_drawDestinationCard() {
		//---
		// If the Destination card deck is empty:
		//	 throw new Exception("There are no destination cards left in the deck");
		//
		// Draw two destination cards from the top of the deck
		// Hold them out from the player's hand of destination cards
		// Go to PickingDestCardsState
		//---
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) {
		// throw new Exception("You cannot discard destination cards you've already picked");
	}

	@Override
	void state_endTurn() {
		// throw new Exception("You must choose an action to take on your turn");
	}

	@Override
	void state_addToChat(String msg) {
		// add the message to the chat
		// update the player's ClientProxy
		return;
	}
}