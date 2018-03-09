package tickets.common.response;

import tickets.common.ChoiceDestinationCards;
import tickets.common.Game;
import tickets.common.DestinationCard;
import tickets.common.HandTrainCard;
import tickets.common.TrainCard;

public class StartGameResponse extends Response {

    private Game game;
    private HandTrainCard playerHand;
    private ChoiceDestinationCards destCardOptions;

    public StartGameResponse(Exception exception) {
        super(exception);
    }

    public StartGameResponse(Game game, HandTrainCard playerHand, ChoiceDestinationCards destCardOptions) {
        this.game = game;
        this.playerHand = playerHand;
        this.destCardOptions = destCardOptions;
    }

    public Game getGame() {
        return game;
    }

    public HandTrainCard getPlayerHand() {
        return playerHand;
    }

    public ChoiceDestinationCards getDestCardOptions() {
        return destCardOptions;
    }
}
