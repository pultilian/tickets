
package tickets.server.model.game;

import tickets.common.TrainCard;
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
	TrainCard state_drawTrainCard() throws Exception {
		//---
		// Draw the first card in the deck
		// Add it to the player's hand
		// End the turn
		//---
		return null;
	}

	@Override
	TrainCard state_drawFaceUpCard(int position) throws Exception {
		//---
		// Check the card at the given position
		// If it is a wild:
		//   throw new Exception("You cannot draw a wild card second");
		// Otherwise:
		// 	 draw it from the row of face up cards
		//   Add it to the player's hand
		// 	 End the turn
		//---
		return null;
	}

	@Override
	void state_claimRoute(Route route) throws Exception {
		throw new Exception("You must draw a second Train card");
	}

	@Override
	DestinationCard state_drawDestinationCard() throws Exception {
		throw new Exception("You must draw a second Train card");
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) throws Exception {
		throw new Exception("You must draw a second Train card");
	}

	@Override
	void state_endTurn() throws Exception {
		throw new Exception("You must draw a second Train card");
	}

	@Override
	void state_addToChat(String msg) {
		// add the message to the chat
		// update the player's ClientProxy
		return;
	}
}