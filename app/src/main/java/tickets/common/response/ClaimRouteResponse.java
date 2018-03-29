package tickets.common.response;

import java.util.Map;

import tickets.common.RouteColors;

public class ClaimRouteResponse extends Response {

    private Map<RouteColors, Integer> removeCards;

    public ClaimRouteResponse(Exception e){
        super(e);
    }

    public ClaimRouteResponse(Map<RouteColors, Integer> removeCards){
        this.removeCards = removeCards;
    }

    public Map<RouteColors, Integer> getRemoveCards() {
        return removeCards;
    }
}
