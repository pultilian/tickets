package tickets.common.response;

import tickets.common.Game;
import tickets.common.DestinationCard;
import tickets.common.TrainCard;

public class StartGameResponse extends Response {

    private Game game;
    private TrainCard[] playerHand;
    private DestinationCard[] destCardOptions;

    public StartGameResponse(Exception exception) {
        super(exception);
    }

    public StartGameResponse(Game game, TrainCard[] playerHand, DestinationCard[] destCardOptions) {
        this.game = game;
        this.playerHand = playerHand;
        this.destCardOptions = destCardOptions;
    }

    public Game getGame() {
        return game;
    }

    public TrainCard[] getPlayerHand() {
        return playerHand;
    }

    public DestinationCard[] getDestCardOptions() {
        return destCardOptions;
    }
}
