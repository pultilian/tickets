
package tickets.common;

import java.util.List;
import java.util.ArrayList;

import tickets.common.TrainCard;

public class HandTrainCard {
	private List<TrainCard> purpleCards;
	private List<TrainCard> blueCards;
	private List<TrainCard> orangeCards;
	private List<TrainCard> whiteCards;
	private List<TrainCard> greenCards;
	private List<TrainCard> yellowCards;
	private List<TrainCard> blackCards;
	private List<TrainCard> redCards;
	private List<TrainCard> wildCards;

	public HandTrainCard() {
		purpleCards = new ArrayList<>();
		blueCards = new ArrayList<>();
		orangeCards = new ArrayList<>();
		whiteCards = new ArrayList<>();
		greenCards = new ArrayList<>();
		yellowCards = new ArrayList<>();
		blackCards = new ArrayList<>();
		redCards = new ArrayList<>();
		wildCards = new ArrayList<>();
	}


	public List<TrainCard> getAllCards() {
		return null;
	}

	public void addCard(TrainCard card) {
		return;
	}

	public void removeCard(TrainCard card) {
		return;
	}

	public List<Integer> getHandSize() {
		return null;
	}
}