
package tickets.server.model.game;

import java.util.List;

import tickets.common.DestinationCard;
import tickets.common.Player;
import tickets.common.Route;
import tickets.common.TrainCard;
import tickets.server.model.game.ServerGame.IServerPlayer;

// A player in a game on the server
// Defines actions that players can take on the game
//
public class ServerPlayer extends IServerPlayer {

	// State Pattern object
	private PlayerTurnState turnState;
	private PlayerTurnState nextState;

	private List<DestinationCard> tempDestinationCards;

	public ServerPlayer(Player copy, ServerGame game) {
		game.super(copy);
		turnState = new GameLoadingState(this);
		nextState = new TurnZeroState(this);
	}

	public ServerPlayer(String playerID, String authToken, ServerGame game) {
		game.super(playerID, authToken);
		turnState = new GameLoadingState(this);
		nextState = new TurnZeroState(this);
	}

	public void initPlayer(List<TrainCard> hand, List<DestinationCard> destinations) {
		// turnState = new TurnZeroState(this);

		for (TrainCard card : hand) {
			this.addTrainCardToHand(card);
		}

		for (DestinationCard dest : destinations) {
			this.addDestinationCardToHand(dest);
		}

		return;
	}

	public void goNextState() {
		if (nextState != null) {
			turnState = nextState;
			nextState = null;
		}
		return;
	}

	@Override
	void startTurn() {
		if (this.nextState != null && this.nextState.getClass() == TurnZeroState.class) {
			goNextState();
			return;
		}
		this.turnState = new TurnStartState(this);
		return;
	}

	@Override
	public TrainCard takeAction_drawTrainCard() throws Exception {
		TrainCard card = turnState.state_drawTrainCard();
		goNextState();
		return card;
	}

	@Override
	public TrainCard takeAction_drawFaceUpCard(int position) throws Exception {
		TrainCard card = turnState.state_drawFaceUpCard(position);
		goNextState();
		return card;
	}

	@Override
	public void takeAction_claimRoute(Route route) throws Exception {
		turnState.state_claimRoute(route);
		goNextState();
		return;
	}

	@Override
	public List<DestinationCard> takeAction_drawDestinationCards() throws Exception {
		tempDestinationCards = turnState.state_drawDestinationCards();
		goNextState();
		return tempDestinationCards;
	}

	@Override
	public void takeAction_discardDestinationCard(DestinationCard discard) throws Exception {
		turnState.state_discardDestinationCard(discard);
		goNextState();
		return;
	}

	@Override
	public void takeAction_endTurn() throws Exception {
		turnState.state_endTurn();
		goNextState();
		return;
	}

	@Override
	public void takeAction_addToChat(String msg) {
		turnState.state_addToChat(msg);
		goNextState();
		return;
	}

	// Using the State Pattern to represent player turn actions;
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
					player.nextState = new WaitForTurnState(player);
					break;
				case DRAWING_TRAIN_CARDS:
					player.nextState = new DrawingTrainCardsState(player);
					break;
				case PICKING_DEST_CARDS:
					player.nextState = new PickingDestCardsState(player);
					break;
				default:
					System.err.println("ERROR: in ServerPlayer.PlayerTurnState, invalid state transition");
					break;
			}

			return;
		}

		protected TrainCard drawTrainCard_fromPlayer() {
			return player.drawTrainCard_fromGame();
		}

		protected TrainCard drawFaceUpCard_fromPlayer(int position) {
			return player.drawFaceUpCard_fromGame(position);
		}

		protected boolean isFaceUpCardWild_fromPlayer(int position) {
			return player.isFaceUpCardWild_fromGame(position);
		}

		protected boolean claimRoute_fromPlayer(Route route) {
			// player.claimRoute_fromGame(route, this)

			return false;
		}

		protected DestinationCard drawDestinationCard_fromPlayer() {
			return player.drawDestinationCard_fromGame();
		}

		protected void discardDestinationCard_fromPlayer(DestinationCard discard) {
			player.discardDestinationCard_fromGame(discard);
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

		protected void endTurn_fromPlayer() {
			player.endTurn_fromGame();
			return;
		}

		protected void addTrainCardToHand_fromPlayer(TrainCard card) {
			player.addTrainCardToHand(card);
			return;
		}

		protected void addDestinationCardToHand_fromPlayer(DestinationCard card) {
			player.addDestinationCardToHand(card);
			return;
		}

		protected List<DestinationCard> getTempDestinationCards_fromPlayer() {
			return player.tempDestinationCards;
		}
	}

	// package-private flags for the
	// different states a player may be in
	// so states may transition from one to another
	enum States {
		WAIT_FOR_TURN,
		DRAWING_TRAIN_CARDS,
		PICKING_DEST_CARDS
	}
}