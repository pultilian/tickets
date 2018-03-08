
package tickets.server.model.game;

import tickets.common.DestinationCard;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player has been dealt their starting
//	 hand of train cards and their three destination cards.
//	 The player must now choose which of the destination cards
//	 to discard - or to keep all of them.
// The player then waits for their turn to start.
//
class TurnZeroState extends PlayerTurnState {
	TurnZeroState(ServerPlayer player) {
		player.super();
	}

	@Override
	void state_drawTrainCard() {

	}

	@Override
	void state_drawFaceUpCard(int position) {

	}

	@Override
	void state_claimRoute(Route route, int numWildCards) {

	}

	@Override
	void state_drawDestinationCard() {

	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) {

	}

	@Override
	void state_endTurn() {

	}

	@Override
	void state_addToChat(String msg) {

	}
}