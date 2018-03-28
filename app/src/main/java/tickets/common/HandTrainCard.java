
package tickets.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandTrainCard {
	private Map<RouteColors, List<TrainCard>> colorsListMap;

	public HandTrainCard() {
	    colorsListMap = new HashMap<>();
	    for (RouteColors color : RouteColors.values()){
	    	colorsListMap.put(color, new ArrayList<TrainCard>());
		}
	}

	public Map<RouteColors, List<TrainCard>> getAllCards() {
		return colorsListMap;
	}

	public int getCountForColor(RouteColors color){
		return colorsListMap.get(color).size();
	}

	public void addCard(TrainCard card) {
		colorsListMap.get(card.getColor()).add(card);
	}

	public TrainCard removeCard(RouteColors color) {
		if(colorsListMap.get(color).size() > 0){
		    return colorsListMap.get(color).remove(0);
        }
        return null;
	}

	public int getHandSize() {
	    int handTotal = 0;
		for(RouteColors key : colorsListMap.keySet()){
		    handTotal += colorsListMap.get(key).size();
        }
        return handTotal;
	}

    public List<TrainCard> getCardsForRoute(Route route) {
        RouteColors color = route.getColor();
	    List<TrainCard> cardsForClaim = colorsListMap.get(color);

	    // TODO: handle case where route is gray
	    int i = 0;
	    while (cardsForClaim.size() != route.getLength()) {
	        List<TrainCard> colorCards = colorsListMap.get(color);
	        List<TrainCard> wildCards = colorsListMap.get(RouteColors.Gray);
	        int j = i - colorCards.size();
	        if (i < colorCards.size())
    	        cardsForClaim.add(colorCards.get(i));
	        else if (j < wildCards.size()) {
	            cardsForClaim.add(wildCards.get(j));
            }
            else {
	            return null;
            }
            i++;
        }

        return cardsForClaim;
    }

    public void removeCards(RouteColors color, int amount) {
	    List<TrainCard> colorCards = colorsListMap.get(color);
	    while(amount > 0 && colorCards.size() > 0) {
            colorCards.remove(colorCards.size() - 1);
            amount --;
        }
    }
}