
package tickets.server.model.game;

import java.util.List;

import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.HandDestinationCard;
import tickets.common.HandTrainCard;
import tickets.common.Route;
import tickets.common.Player;

import tickets.server.model.game.ServerGame;
import tickets.server.model.game.ServerGame.IServerPlayer;

// A player in a game on the server
// Defines actions that players can take on the game
//
public class ServerPlayer extends IServerPlayer {

	// State Pattern object
	private PlayerTurnState turnState;

	public ServerPlayer(Player copy, ServerGame game) {
		game.super(copy);
		turnState = new GameLoadingState(this);
	}

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

	@Override
	void startTurn() {
		this.turnState = new TurnStartState(this);
	}

	@Override
	public TrainCard takeAction_drawTrainCard() throws Exception {
		return turnState.state_drawTrainCard();
	}

	@Override
	public TrainCard takeAction_drawFaceUpCard(int position) throws Exception {
		return turnState.state_drawFaceUpCard(position);
	}

	@Override
	public void takeAction_claimRoute(Route route) throws Exception {
		turnState.state_claimRoute(route);
		return;
	}

	@Override
	public List<DestinationCard> takeAction_drawDestinationCards() throws Exception {
		return turnState.state_drawDestinationCards();
	}

	@Override
	public void takeAction_discardDestinationCard(DestinationCard discard) throws Exception {
		turnState.state_discardDestinationCard(discard);
		return;
	}

	@Override
	public void takeAction_endTurn() throws Exception {
		turnState.state_endTurn();
		return;
	}

	@Override
	public void takeAction_addToChat(String msg) {
		turnState.state_addToChat(msg);
		return;
	}

	// Using the State Pattern to represent player turn actions
	// 	the server does not need to check player state before
	// 	executing a client's game commands (this simplifies the
	//	multi-step processes of drawing train/destination cards)
	//
	abstract class PlayerTurnState {
		private ServerPlayer player;

		PlayerTurnState() {
			player = ServerPlayer.this;
		}

		abstract TrainCard state_drawTrainCard() throws Exception;
		abstract TrainCard state_drawFaceUpCard(int position) throws Exception;
		abstract void state_claimRoute(Route route) throws Exception;
		abstract List<DestinationCard> state_drawDestinationCards() throws Exception;
		abstract void state_discardDestinationCard(DestinationCard discard) throws Exception;
		abstract void state_endTurn() throws Exception;
		abstract void state_addToChat(String msg);


		protected void changeStateTo(States state) {
			switch(state) {
				case WAIT_FOR_TURN:	
					player.turnState = new WaitForTurnState(player);
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
		WAIT_FOR_TURN,
		DRAWING_TRAIN_CARDS,
		PICKING_DEST_CARDS
	}
}