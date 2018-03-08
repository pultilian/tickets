
package tickets.server.model.game;

import tickets.common.DestinationCard;

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
	void drawTrainCard() {
		//---
		// Draw the top card of the Train card deck
		// Add it to the player's hand
		// Go to DrawingTrainCardsState
		//---
		return;
	}

	@Override
	void drawFaceUpCard(int position) {
		//---
		// Draw the face up card at the specified position
		// Add it to the player's hand
		// If the card drawn is wild:
		//	 end the turn
		// Otherwise:
		//	 Go to DrawingTrainCardsState
		//---
		return;
	}

	@Override
	void claimRoute(Route route, int numWildCards) {
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
		return;
	}

	@Override
	void drawDestinationCard() {
		//---
		// Draw two destination cards from the top of the deck
		// Hold them out from the player's hand of destination cards
		// Go to PickingDestCardsState
		//---
		return;
	}

	@Override
	void discardDestinationCard(DestinationCard discard) {
		// Players cannot discard destination cards they have already picked
		return;
	}

	@Override
	void endTurn() {
		// Players must choose an action to take on their turn
		return;
	}

	@Override
	void addToChat(String msg) {
		// Add the message to the chat
		return;
	}
}