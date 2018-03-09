
package tickets.common.response;

import java.util.List;

import tickets.common.DestinationCard;

public class DestinationCardResponse extends Response {
	private List<DestinationCard> cards;

	public DestinationCardResponse(Exception ex) {
			super(ex);
    }

	public DestinationCardResponse(List<DestinationCard> cards) {
		this.cards = cards;
	}

	public List<DestinationCard> getCards() {
		return cards;
	}
}