
package tickets.server.model.game;

import java.util.List;

import tickets.common.DestinationCard;
import tickets.common.Route;
import tickets.common.TrainCard;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player has already drawn one train card - 
//   either from the top of the deck or from the non-wild cards 
//   in the row of face-up cards.
// The player must now choose another train card to draw, but
//   may not draw a face-up wild card. After choosing, the
// 	 player's turn ends.
//
class DrawingTrainCardsState extends PlayerTurnState {
	DrawingTrainCardsState(ServerPlayer player) {
		player.super();
	}

	@Override
	TrainCard state_drawTrainCard() throws Exception {
		TrainCard card = drawTrainCard_fromPlayer();
		if (card == null) {
			throw new Exception("There are no train cards in the deck to be drawn");
		}
		addTrainCardToHand_fromPlayer(card);
		changeStateTo(ServerPlayer.States.DRAWING_TRAIN_CARDS);
		return card;
	}

	@Override
	TrainCard state_drawFaceUpCard(int position) throws Exception {
		if (isFaceUpCardWild_fromPlayer(position)) {
			throw new Exception("You cannot draw a wild card now.");
		}

		TrainCard card = drawFaceUpCard_fromPlayer(position);
		if (card == null) {
			throw new Exception("There are no face up train cards to be drawn");
		}
		
		addTrainCardToHand_fromPlayer(card);
		changeStateTo(ServerPlayer.States.DRAWING_TRAIN_CARDS);
		return card;
	}

	@Override
	void state_claimRoute(Route route) throws Exception {
		throw new Exception("You must draw a second Train card");
	}

	@Override
	List<DestinationCard> state_drawDestinationCards() throws Exception {
		throw new Exception("You must draw a second Train card");
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) throws Exception {
		throw new Exception("You must draw a second Train card");
	}

	@Override
	void state_endTurn() throws Exception {
		throw new Exception("You must draw a second Train card");
	}

	@Override
	void state_addToChat(String msg) {
		addToChat_fromPlayer(msg);
		return;

	}
}