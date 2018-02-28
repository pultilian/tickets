
package tickets.common.response;

import tickets.common.TrainCard;

public class TrainCardResponse extends Response {
		private TrainCard card;

		public TrainCardResponse(Exception ex) {
			super(ex);
		}

		public TrainCardResponse(TrainCard card) {
			this.card = card;
		}

 		public TrainCard getCard() {
			return card;
		}
}