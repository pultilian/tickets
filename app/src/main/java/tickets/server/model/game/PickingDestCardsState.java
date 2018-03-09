
package tickets.server.model.game;

import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Route;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player has drawn two destination cards
//	 and must now choose whether to discard one or zero of them.
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
	DestinationCard state_drawDestinationCard() throws Exception {
		throw new Exception("You must decide which destination cards to keep");
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) throws Exception {
		//---
		// Discard the specified destination card.
		// Update the game's history
		// End the turn.
		//---
		return;
	}

	@Override
	void state_endTurn() throws Exception {
		//---
		// Keep both of the destination cards that were drawn
		// Update the game's history (?)
		// End the turn
		//---
		return;
	}

	@Override
	void state_addToChat(String msg) {
		// add the message to the chat
		// update the player's ClientProxy
		return;
	}
}