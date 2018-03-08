
package tickets.server.model.game;

import tickets.common.DestinationCard;

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

	void drawTrainCard() {
		//---
		// Draw the first card in the deck
		// Add it to the player's hand
		// End the turn
		//---
		return;
	}

	// The player has already drawn one train card,
	//   so they can't draw a face up card if it is a wild
	//
	void drawFaceUpCard(int position) {
		//---
		// Check the card at the given position
		// If it is a wild:
		//   do nothing
		// Otherwise:
		// 	 draw it from the row of face up cards
		//   Add it to the player's hand
		// 	 End the turn
		//---
		return;
	}

	// These functions do not apply to this state
	void claimRoute(Route route, int numWildCards) {
		// the player must choose a second card to draw
		return;
	}
	void drawDestinationCard() {
		// the player must choose a second card to draw
		return;
	}
	void discardDestinationCard(DestinationCard discard) {
		// the player must choose a second card to draw
		return;
	}
	void endTurn() {
		// the player must choose a second card to draw
		return;
	}
}