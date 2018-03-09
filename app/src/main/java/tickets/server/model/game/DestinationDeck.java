
package tickets.server.model.game;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import tickets.common.DestinationCard;
import tickets.common.AllDestinationCards;

public class DestinationDeck {
	private List<DestinationCard> cards;

	public DestinationDeck(List<DestinationCard> cards) {
		this.cards = AllDestinationCards.getCards();
		Collections.shuffle(cards, new Random());
	}

	public DestinationCard drawCard() {
		return cards.remove(0);
	}

	public boolean discardCard(DestinationCard discard) {
		if (cards.contains(discard)) {
			return false;
		}
		cards.add(discard);
		return true;
	}
}