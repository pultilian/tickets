
package tickets.common.response;

import tickets.common.DestinationCard;

public class DestinationCardResponse extends Response {
		private DestinationCard card1;
		private DestinationCard card2;

		public DestinationCardResponse(Exception ex) {
			super(ex);
		}

		public DestinationCardResponse(DestinationCard card1, DestinationCard card2) {
			this.card1 = card1;
			this.card2 = card2;
		}

		public DestinationCard getCard1() {
			return card1;
		}

		public DestinationCard getCard2() {
			return card2;
		}
}