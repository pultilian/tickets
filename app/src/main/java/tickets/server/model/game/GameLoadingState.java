
package tickets.server.model.game;

import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Route;

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
	TrainCard state_drawTrainCard() throws Exception {
		throw new Exception("The game is still loading");
	}

	@Override
	TrainCard state_drawFaceUpCard(int position) throws Exception {
		throw new Exception("The game is still loading");
	}

	@Override
	void state_claimRoute(Route route) throws Exception {
		throw new Exception("The game is still loading");
	}

	@Override
	DestinationCard state_drawDestinationCard() throws Exception {
		throw new Exception("The game is still loading");
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) throws Exception {
		throw new Exception("The game is still loading");
	}

	@Override
	void state_endTurn() throws Exception {
		throw new Exception("The game is still loading");
	}

	@Override
	void state_addToChat(String msg) {
		// add the message to the chat
		// update the player's ClientProxy
		return;
	}

}