
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