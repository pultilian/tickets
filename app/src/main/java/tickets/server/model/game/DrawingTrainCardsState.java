
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