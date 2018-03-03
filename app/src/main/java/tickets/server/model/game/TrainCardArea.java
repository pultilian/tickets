
package tickets.server.model.game;

import java.util.List;
import java.util.ArrayList;

import tickets.common.TrainCard;

public class TrainCardArea {
	private List<TrainCard> drawDeck;
	private List<TrainCard> discardDeck;

	private TrainCard[] faceUpCards;

	public TrainCardArea(List<TrainCard> allCards) {
		//---
		drawDeck = new ArrayList<>();
		discardDeck = new ArrayList<>();

		faceUpCards = new TrainCard[5];
		for (int i = 0; i < faceUpCards.length; i++) {
			faceUpCards[i] = drawCard();
		}
		//---
	}

	public TrainCard drawCard() {
		//---
		// Is the deck shuffled on creation, or accessed randomly?
		//---
		return null;
	}

	public TrainCard drawFaceUpCard(int position) {
		if (position < 1 || position > 5) {
			return null;
		}
		TrainCard draw = faceUpCards[position - 1];
		faceUpCards[position - 1] = drawCard();
		return draw;
	}

	public boolean discardCard(TrainCard discard) {
		if (drawDeck.contains(discard) || discardDeck.contains(discard)) {
			return false;
		}
		for (int i = 0; i < faceUpCards.length; i++) {
			if (discard == faceUpCards[i]) return false;
		}

		discardDeck.add(discard);
		return true;
	}
}