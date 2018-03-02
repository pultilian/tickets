
package tickets.server.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import tickets.common.DestinationCard;

public class DestinationDeck {
	private List<DestinationCard> cards;

	public DestinationDeck(List<DestinationCard> cards) {
		//add all destination cards in the game to this.cards
		//	should the deck create all the cards? Or the game that owns it?
		//	I think the decks should create the cards; it's maybe less secure, since the game doesn't -own-
		//	references to the cards, but it's less of a headache

		// It makes more sense to me for the game to create the cards (as the cards come with the game box).
		// It'll be the same chuck of code either way, just in a different place. The game itself
		// won't -own- the card references anyways.
		// That's actually more secure because less things can access the cards.

		this.cards = cards;
		Collections.shuffle(cards, new Random());
	}

	public DestinationCard drawCard() {
		return cards.remove(0);
	}

	public boolean discardCard(DestinationCard discard) {
		if (cards.contains(discard)) {
			// Just wondering, when would this happen?
			return false;
		}
		cards.add(discard);
		return true;
	}
}