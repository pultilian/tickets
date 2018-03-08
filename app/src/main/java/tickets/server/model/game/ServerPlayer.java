
package tickets.server.model.game;

import java.util.List;

import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.HandDestinationCard;
import tickets.common.HandTrainCard;

import tickets.server.model.game.ServerGame;
import tickets.server.model.game.ServerGame.IServerPlayer;

// A player in a game on the server
// Defines actions that players can take on the game
//
public class ServerPlayer extends IServerPlayer {

	// State Pattern object
	private PlayerTurnState turnState;

	public ServerPlayer(String playerID, String authToken, ServerGame game) {
		game.super(playerID, authToken);
		turnState = new GameLoadingState(this);
	}

	public void initPlayer(List<TrainCard> hand, List<DestinationCard> destinations) {
		turnState = new TurnZeroState(this);

		HandTrainCard playerCards = this.getHandTrainCards();
		for(TrainCard card : hand) {
			playerCards.addCard(card);
		}

		HandDestinationCard playerDestinations = this.getHandDestinationCards();
		for(DestinationCard dest : destinations) {
			playerDestinations.addCard(dest);
		}

		return;
	}

	// public void drawTrainCard() {
	// 	turnState.drawTrainCard();
	// 	return;
	// }

	// public void drawFaceUpCard(int position) {
	// 	return;
	// }

	// 
	// depends on how routes, cities, and the game's map are represented 
	// claimRoute(String srcCity, String destCity, RouteColors color, int )
	// public void claimRoute(Route route, int numWildCards) {
	// 	return;
	// }

	// public void drawDestinationCard() {
	// 	return;
	// }

	// public void discardDestinationCard(DestinationCard discard) {
	// 	return;
	// }


	//Using the State Pattern to represent player turn actions
	//	the server does not need to check player state before
	//	executing a client's game commands (this simplifies the
	//	multi-step processes of drawing train/destination cards)
	//
	abstract class PlayerTurnState {
		private ServerPlayer player;

		PlayerTurnState() {
			player = ServerPlayer.this;
		}

		abstract void state_drawTrainCard();
		abstract void state_drawFaceUpCard(int position);
		abstract void state_claimRoute(Route route, int numWildCards);
		abstract void state_drawDestinationCard();
		abstract void state_discardDestinationCard(DestinationCard discard);
		abstract void state_endTurn();
		abstract void state_addToChat(String msg);


		protected void changeStateTo(States state) {
			switch(state) {
				case TURN_ZERO:
					player.turnState = new TurnZeroState(player);
					break;
				case WAIT_FOR_TURN:	
					player.turnState = new WaitForTurnState(player);
					break;
				case TURN_START:
					player.turnState = new TurnStartState(player);
					break;
				case DRAWING_TRAIN_CARDS:
					player.turnState = new DrawingTrainCardsState(player);
					break;
				case PICKING_DEST_CARDS:
					player.turnState = new PickingDestCardsState(player);
					break;
				default:
					System.out.println("ERROR: in ServerPlayer.PlayerTurnState, invalid state transition");
					break;
			}

			return;
		}

		protected void addToHistory_fromPlayer(String msg) {
			player.addToHistory_fromGame(msg);
			return;
		}
		protected void addToChat_fromPlayer(String msg) {
			player.addToChat_fromGame(msg);
			return;
		}
	}

	// package-private - flags for the
	// different states a player may be in
	// so states may transition from one to another
	enum States {
		TURN_ZERO,
		WAIT_FOR_TURN,
		TURN_START,
		DRAWING_TRAIN_CARDS,
		PICKING_DEST_CARDS
	}
}