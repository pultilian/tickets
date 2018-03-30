package tickets.common.response;

import java.util.List;
import java.util.Map;

import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.TrainCardWrapper;

public class ClaimRouteResponse extends Response {

    private TrainCardWrapper removeCards;

    public ClaimRouteResponse(Exception e){
        super(e);
    }

    public ClaimRouteResponse(TrainCardWrapper removeCards){
        this.removeCards = removeCards;
    }

    public List<TrainCard> getRemoveCards() {
        return removeCards.getTrainCards();
    }
}
