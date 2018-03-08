
package tickets.server.model.game;

import tickets.common.DestinationCard;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, a game has been created from the players'
//	 lobby but is not yet finished loading. This state serves
//	 as a waiting area until the game is ready to accept player
//	 commands. As such, this state does not transition to
//	 any other independently.
//
class GameLoadingState extends PlayerTurnState {

	GameLoadingState(ServerPlayer player) {
		player.super();
	}

	// Players are currently loading into the game,
	//   so no player actions can be taken

	@Override
	void drawTrainCard() {
		return;
	}

	@Override
	void drawFaceUpCard(int position) {
		return;
	}

	@Override
	void claimRoute(Route route, int numWildCards) {
		return;
	}

	@Override
	void drawDestinationCard() {
		return;
	}

	@Override
	void discardDestinationCard(DestinationCard discard) {
		return;
	}

	@Override
	void addToChat(String msg) {
		//add the message to the chat
		return;
	}
}