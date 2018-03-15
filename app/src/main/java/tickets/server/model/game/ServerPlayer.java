
package tickets.server.model.game;

import java.util.List;

import tickets.common.ChoiceDestinationCards;
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

	//----------------------------------------------------------------------------------------------
    // *** SET-UP METHODS ***

	public ServerPlayer(Player copy) {
		super(copy);
		turnState = DrewDestCardsState.getInstance();
	}

	public void initPlayer(List<TrainCard> hand, List<DestinationCard> destinations) {
		for (TrainCard card : hand) {
			this.addTrainCardToHand(card);
		}

        ChoiceDestinationCards choices = new ChoiceDestinationCards();
		choices.setDestinationCards(destinations);
		this.setDestinationCardOptions(choices);
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
    // Returns null on success and an error message on failure

	public String drawTrainCard(TrainCard card) {
	    return turnState.drawTrainCard(card, this);
    }

    public String drawFaceUpCard(TrainCard card) {
        return turnState.drawFaceUpCard(card, this);
    }

    public String claimRoute(Route route) {
	    return turnState.claimRoute(route, this);
    }

    public String drawDestinationCards(List<DestinationCard> cards) {
	    return turnState.drawDestinationCards(cards, this);
    }

    public String discardDestinationCard(DestinationCard card) {
	    return turnState.discardDestinationCard(card, this);
    }

    public String endTurn() {
	    return turnState.endTurn(this);
    }
}