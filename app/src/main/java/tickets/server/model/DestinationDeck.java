
package tickets.server.model;

import java.util.List;
import java.util.ArrayList;

import tickets.common.DestinationCard;

public class DestinationDeck {
	private List<DestinationCard> cards;

	public DestinationDeck(List<DestinationCard> allCards) {
		//add all destination cards in the game to this.cards
		//	should the deck create all the cards? Or the game that owns it?
		//	I think the decks should create the cards; it's maybe less secure, since the game doesn't -own-
		//	references to the cards, but it's less of a headache
		cards = new ArrayList<>();
	}

	public DestinationCard drawCard() {
		//get a random card in the deck
		//OR shuffle the deck once at the start of the game, then just get the top card...
		return null;
	}

	public boolean discardCard(DestinationCard discard) {
		if (cards.contains(discard)) {
			return false;
		}
		//put the card on the bottom of the deck by appending it to the end of the list
		//or add it to the discard pile if the deck isn't shuffled on creation
		return true;
	}
}