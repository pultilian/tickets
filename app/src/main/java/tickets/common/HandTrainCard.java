
package tickets.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

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

	public List<TrainCard> getCards(int length, RouteColors selectedColor) {
		List<TrainCard> result = new ArrayList<>();
		// Get as many of the selected color train card as possible
		for (int i = 0; i < min(getCountForColor(selectedColor), length); i++)
			result.add(colorsListMap.get(selectedColor).get(i));

		// Fill in remaining train cards with wild cards
		if (result.size() < length) {
			int remaining = length - result.size();
			// Not enough cards
			if (getCountForColor(RouteColors.Wild) < remaining) return null;
			for (int i = 0; i < remaining; i++)
				result.add(colorsListMap.get(RouteColors.Wild).get(i));
		}
		return result;
	}

    public void removeCards(RouteColors color, int amount) {
	    List<TrainCard> colorCards = colorsListMap.get(color);
	    while(amount > 0 && colorCards.size() > 0) {
            colorCards.remove(colorCards.size() - 1);
            amount --;
        }
    }
}