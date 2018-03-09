
package tickets.server.model.game;

import tickets.common.DestinationCard;
import tickets.common.Route;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player has already drawn one train card - 
//   either from the top of the deck or from the non-wild cards 
//   in the row of face-up cards.
// The player must now choose another train card to draw, but
//   may not draw a face-up wild card. After choosing, the
// 	 player's turn ends.
//
class DrawingTrainCardsState extends PlayerTurnState {
	DrawingTrainCardsState(ServerPlayer player) {
		player.super();
	}

	@Override
	void state_drawTrainCard() {

	}

	@Override
	void state_drawFaceUpCard(int position) {
		//---
		// Check the card at the given position
		// If it is a wild:
		//   throw new Exception("You cannot draw a wild card second");
		// Otherwise:
		// 	 draw it from the row of face up cards
		//   Add it to the player's hand
		// 	 End the turn
		//---
	}

	@Override
	void state_claimRoute(Route route) {
		// throw new Exception("You must draw a second Train card");
		return;
	}

	@Override
	void state_drawDestinationCard() {


		// throw new Exception("You must draw a second Train card");
		return;

	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) {
		// throw new Exception("You must draw a second Train card");
		return;

	}

	@Override
	void state_endTurn() {
		// throw new Exception("You must draw a second Train card");
		return;

	}

	@Override
	void state_addToChat(String msg) {
		// add the message to the chat
		// update the player's ClientProxy
		return;

	}
}