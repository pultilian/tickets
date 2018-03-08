
package tickets.server.model.game;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import tickets.common.DestinationCard;
import tickets.common.AllDestinationCards;

public class DestinationDeck {
	private List<DestinationCard> cards;

	public DestinationDeck(List<DestinationCard> cards) {
		this.cards = AllDestinationCards.getDeck();
		Collections.shuffle(cards, new Random());
	}

	public DestinationCard drawCard() {
		return cards.remove(0);
	}

	public boolean discardCard(DestinationCard discard) {
		if (cards.contains(discard)) {
			// Just wondering, when would this happen?

			// I have no earthly idea. Just though it'd be a good defensive programming practice
			return false;
		}
		cards.add(discard);
		return true;
	}
}