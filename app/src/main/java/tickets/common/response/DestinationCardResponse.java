
package tickets.common.response;

import tickets.common.DestinationCard;

public class DestinationCardResponse extends Response {
		private DestinationCard card;

		public DestinationCardResponse(Exception ex) {
			super(ex);
		}

		public DestinationCardResponse(DestinationCard card) {
			this.card = card;
		}

		public DestinationCard getCard() {
			return card;
		}
}