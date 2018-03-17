
package tickets.common;

import java.util.ArrayList;
import java.util.List;

public class HandDestinationCard {
	private List<DestinationCard> cards;

	public HandDestinationCard() {
		cards = new ArrayList<>();
	}

	public List<DestinationCard> getAllCards() {
		return cards;
	}

	public void addCard(DestinationCard card) {
		cards.add(card);
	}

	public boolean removeCard(DestinationCard card) {
		return cards.remove(card);
	}

	public int getHandSize() {
		return cards.size();
	}
}