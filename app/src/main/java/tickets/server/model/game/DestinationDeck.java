
package tickets.server.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tickets.common.DestinationCard;

public class DestinationDeck {
	private List<DestinationCard> cards;

	public DestinationDeck(List<DestinationCard> cards) {
		this.cards = cards;
		Collections.shuffle(this.cards, new Random());
	}

	public List<DestinationCard> getTopThreeCards() {
	    if (cards.isEmpty()) return null;
	    List<DestinationCard> result = new ArrayList<>();
	    result.add(cards.get(0));
	    if (cards.size() > 1) result.add(cards.get(1));
	    if (cards.size() > 2) result.add(cards.get(2));
	    return result;
    }

	public List<DestinationCard> drawCards() {
		List<DestinationCard> result = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
		    if (!cards.isEmpty()) result.add(cards.remove(0));
        }
        return result;
	}

	public boolean discardCards(List<DestinationCard> cards) {
		for (DestinationCard discard : cards) {
			if (this.cards.contains(discard)) {
				return false;
			}
			this.cards.add(discard);
		}
		return true;
	}
}