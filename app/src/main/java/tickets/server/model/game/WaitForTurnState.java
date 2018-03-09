
package tickets.server.model.game;

import java.util.List;

import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Route;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player is waiting for their turn to start.
//	 The player cannot interact with the game, but can still
//	 send chat messages.
//
class WaitForTurnState extends PlayerTurnState {
	WaitForTurnState(ServerPlayer player) {
		player.super();
	}

	@Override
	TrainCard state_drawTrainCard() throws Exception {
		throw new Exception("It is not your turn");
	}

	@Override
	TrainCard state_drawFaceUpCard(int position) throws Exception {
		throw new Exception("It is not your turn");
	}

	@Override
	void state_claimRoute(Route route) throws Exception {
		throw new Exception("It is not your turn");
	}

	@Override
<<<<<<< HEAD
	List<DestinationCard> state_drawDestinationCards() throws Exception {
		throw new Exception("It is not your turn");
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) throws Exception {
		throw new Exception("It is not your turn");
	}

	@Override
	void state_endTurn() throws Exception {
		throw new Exception("It is not your turn");
=======
	void state_drawDestinationCard() {

		// throw new Exception("It is not your turn");
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) {

		// throw new Exception("It is not your turn");
	}

	@Override
	void state_endTurn() {

		// throw new Exception("It is not your turn");
>>>>>>> master
	}

	@Override
	void state_addToChat(String msg) {

		// add the message to the chat
		// update the player's ClientProxy
		return;
	}
}