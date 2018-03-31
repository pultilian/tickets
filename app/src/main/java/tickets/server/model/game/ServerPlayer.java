
package tickets.server.model.game;

import java.util.List;

import tickets.common.ChoiceDestinationCards;
import tickets.common.PlayerSummary;
import tickets.common.Route;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Player;

public class ServerPlayer extends Player {

    /*Inherited from Player:
    private String playerId;
    private String associatedAuthToken;
    private HandDestinationCard playerDestinationCards;
    private HandTrainCard playerResourceCards;
    private ChoiceDestinationCards destinationCardOptions;*/

//--------------------------------------------------------------------------------------------------
	// State Pattern object
	private PlayerTurnState turnState;

	// Flag strings to be sent back by turn state actions
    static final String END_TURN = "EndTurn";
    static final String LAST_ROUND = "LastRound";

    private PlayerSubmap map;
    private boolean isLastPlayer;
	//----------------------------------------------------------------------------------------------
    // *** SET-UP METHODS ***

	ServerPlayer(Player copy) {
		super(copy);
		turnState = DrewDestCardsState.getInstance();
		isLastPlayer = false;
	}

	void initPlayer(List<TrainCard> hand, List<DestinationCard> destinations) {
		for (TrainCard card : hand) {
			this.addTrainCardToHand(card);
		}

        ChoiceDestinationCards choices = new ChoiceDestinationCards();
		choices.setDestinationCards(destinations);
		this.setDestinationCardOptions(choices);
		map = new PlayerSubmap();
	}

	//----------------------------------------------------------------------------------------------
    // *** GETTERS / SETTERS ***

    boolean isLastPlayer() {
	    return isLastPlayer;
    }

    void becomeLastPlayer() {
	    isLastPlayer = true;
    }

    void addRouteToMap(Route route) {
	    map.addRoute(route);
    }

    int getLongestRouteLength() {
	    return map.findLongestRoute();
    }

    //----------------------------------------------------------------------------------------------
    // *** END GAME CALCULATIONS

    public PlayerSummary calculateSummary(boolean longestRoute) {
	    return new PlayerSummary(getInfo(), calculateSuccessfulDestPoints(), calculateFailedDestPoints(), longestRoute);
    }

    private int calculateSuccessfulDestPoints() {
	    int total = 0;
	    for (DestinationCard card : getHandDestinationCards().getAllCards()) {
	        if (map.pathExists(card.getFirstCity(), card.getSecondCity())) {
	            total += card.getValue();
            }
        }
        return total;
    }

    private int calculateFailedDestPoints() {
	    int total = 0;
	    for (DestinationCard card : getHandDestinationCards().getAllCards()) {
	        if (!map.pathExists(card.getFirstCity(), card.getSecondCity())) {
	            total += card.getValue();
            }
        }
        return total;
    }

	//----------------------------------------------------------------------------------------------
    // *** STATE-CHANGING FUNCTIONS ***

    void changeState(PlayerTurnState.States newState) {
        switch (newState) {
            case NOT_MY_TURN:
                turnState = NotMyTurnState.getInstance();
                break;
            case TURN_START:
                turnState = TurnStartState.getInstance();
                break;
            case DREW_ONE_TRAIN_CARD:
                turnState = DrewOneTrainCardState.getInstance();
                break;
            case DREW_DESTINATION_CARDS:
                turnState = DrewDestCardsState.getInstance();
                break;
            default:
                System.err.println("ERROR: in ServerPlayer, invalid state transition");
                break;
        }
    }

	void startTurn() {
		turnState = TurnStartState.getInstance();
	}

	//----------------------------------------------------------------------------------------------
	// *** PLAYER ACTIONS ***
    // Returns null or end turn string (in the case of drawing train cards) on success and an error message on failure

	public String drawTrainCard(TrainCard card) {
	    return turnState.drawTrainCard(card, this);
    }

    public String drawFaceUpCard(TrainCard card) {
        return turnState.drawFaceUpCard(card, this);
    }

    public String claimRoute(Route route, List<TrainCard> cards) {
	    return turnState.claimRoute(route, cards, this);
    }

    public String drawDestinationCards(List<DestinationCard> cards) {
	    return turnState.drawDestinationCards(cards, this);
    }

    public String discardDestinationCard(List<DestinationCard> cards) {
	    return turnState.discardDestinationCard(cards, this);
    }
}