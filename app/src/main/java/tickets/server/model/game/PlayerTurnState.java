package tickets.server.model.game;

import java.util.List;

import tickets.common.DestinationCard;
import tickets.common.Route;
import tickets.common.TrainCard;

// Using the State Pattern to represent player turn actions.
public abstract class PlayerTurnState {

    // package-private flags for the different possible states
    enum States {
        NOT_MY_TURN,
        TURN_START,
        DREW_ONE_TRAIN_CARD,
        DREW_DESTINATION_CARDS
    }

    abstract String drawTrainCard(TrainCard card, ServerPlayer player);

    abstract String drawFaceUpCard(TrainCard card, ServerPlayer player);

    abstract String claimRoute(Route route, List<TrainCard> cards, ServerPlayer player);

    abstract String drawDestinationCards(List<DestinationCard> cards, ServerPlayer player);

    abstract String discardDestinationCard(List<DestinationCard> card, ServerPlayer player);

    abstract String endTurn(ServerPlayer player);
}