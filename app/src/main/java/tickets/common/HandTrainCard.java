
package tickets.common;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import tickets.common.TrainCard;

public class HandTrainCard {
	private Map<RouteColors, List<TrainCard>> colorsListMap;

	public HandTrainCard() {
	    colorsListMap = new HashMap<>();
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
}