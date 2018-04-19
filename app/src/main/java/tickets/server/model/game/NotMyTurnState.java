
package tickets.server.model.game;

import java.util.List;

import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Route;

// Part of the state pattern describing a player's overall turn
//
// At this state, the player is waiting for their turn to start.
public class NotMyTurnState extends PlayerTurnState {
    private String notMyTurnStateFlag = "flag";

    // Singleton pattern
    private static NotMyTurnState INSTANCE;

    public static NotMyTurnState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotMyTurnState();
        }
        return INSTANCE;
    }

    private NotMyTurnState() {
    }

    @Override
    String drawTrainCard(TrainCard card, ServerPlayer player) {
        return "It is not your turn.";
    }

    @Override
    String drawFaceUpCard(TrainCard card, ServerPlayer player) {
        return "It is not your turn.";
    }

    @Override
    String claimRoute(Route route, List<TrainCard> cards, ServerPlayer player) {
        return "It is not your turn.";
    }

    @Override
    String drawDestinationCards(List<DestinationCard> cards, ServerPlayer player) {
        return "It is not your turn.";
    }

    @Override
    String discardDestinationCard(List<DestinationCard> card, ServerPlayer player) {
        return "It is not your turn.";
    }

    @Override
    String endTurn(ServerPlayer player) {
        // The game will check if you are the current player. If so, this method will be called.
        return null;
    }
}