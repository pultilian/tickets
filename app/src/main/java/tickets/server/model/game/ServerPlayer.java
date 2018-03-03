
package tickets.server.model.game;

import java.util.List;

import tickets.common.Player;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;


public class ServerPlayer extends Player {
	//inherited:
	// public Player(String playerId, String associatedAuthToken);
	// public String getPlayerId();
	// public String getAssociatedAuthToken();
	// public Faction getPlayerFaction();
	// public void setPlayerFaction(Faction playerFaction);
	// public HandTrainCard getTrainCards();

//--------------

	private ServerGame game;
	private PlayerTurnState turnState;

	public ServerPlayer(String playerID, String authToken, ServerGame game) {
		super(playerID, authToken);
		this.game = game;
	}


	public void drawTrainCard() {
		turnState.drawTrainCard();
		return;
	}

	public void drawFaceUpCard(int position) {
		return;
	}

	// 
	// depends on how routes, cities, and the game's map are represented 
	// claimRoute(String srcCity, String destCity, RouteColors color, int )
	public void claimRoute(Route route, int numWildCards) {
		return;
	}

	public void drawDestinationCard() {
		return;
	}

	public void discardDestinationCard(DestinationCard discard) {
		return;
	}


	//Using the State Pattern to represent player turn actions
	//	the server does not need to check player state before
	//	executing a client's game commands (this simplifies the
	//	multi-step processes of drawing train/destination cards)
	abstract class PlayerTurnState {
		protected ServerPlayer player;

		PlayerTurnState() {
			player = ServerPlayer.this;
		}

		abstract void drawTrainCard();
		abstract void drawFaceUpCard(int position);
		abstract void claimRoute(Route route, int numWildCards);
		abstract void drawDestinationCard();
		abstract void discardDestinationCard(DestinationCard discard);

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
	}

	// package-private
	enum States {
		TURN_ZERO,
		WAIT_FOR_TURN,
		TURN_START,
		DRAWING_TRAIN_CARDS,
		PICKING_DEST_CARDS
	}
}