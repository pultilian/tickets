
package tickets.server.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tickets.common.RouteColors;
import tickets.common.TrainCard;

public class TrainCardArea {
	private List<TrainCard> drawDeck;
	private List<TrainCard> discardDeck;

	public TrainCard[] getFaceUpCards() {
		return faceUpCards;
	}

	private TrainCard[] faceUpCards;

	private Random rand;

	public TrainCardArea(List<TrainCard> allCards) {
		drawDeck = new ArrayList<>(allCards);
		discardDeck = new ArrayList<>();

		faceUpCards = new TrainCard[5];
		for (int i = 0; i < faceUpCards.length; i++) {
			faceUpCards[i] = drawCard();
		}
		rand = new Random();

		// Deck is shuffled once on creation
		Collections.shuffle(drawDeck, rand);
	}

	public TrainCard drawCard() {
		if (drawDeck.isEmpty() && discardDeck.isEmpty()) {
			return null;
		}
		TrainCard card = drawDeck.remove(0);
		// Reshuffle discard into deck if deck is empty
		if (drawDeck.isEmpty()) {
			drawDeck.addAll(discardDeck);
			discardDeck.clear();
			Collections.shuffle(drawDeck, rand);
		}
		return card;
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

	public boolean isFaceUpCardWild(int position) {
		if (position < 1 || position > 5) {
			return false;
		}
		if (faceUpCards[position - 1].getColor() == RouteColors.Wild) {
			return true;
		}
		else return false;
	}
}