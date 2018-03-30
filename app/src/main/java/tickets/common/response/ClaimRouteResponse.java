package tickets.common.response;

import java.util.List;
import java.util.Map;

import tickets.common.RouteColors;
import tickets.common.TrainCard;

public class ClaimRouteResponse extends Response {

    private List<TrainCard> removeCards;

    public ClaimRouteResponse(Exception e){
        super(e);
    }

    public ClaimRouteResponse(List<TrainCard> removeCards){
        this.removeCards = removeCards;
    }

    public List<TrainCard> getRemoveCards() {
        return removeCards;
    }
}
