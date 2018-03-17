
package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.DestinationCard;
	// What info do we need?

public interface IDestinationPresenter {
	public List<DestinationCard> getDestinationCards();
	public void chooseDestinationCards(DestinationCard toDiscard);
}