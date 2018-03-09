
package tickets.client.gui.presenters;

import java.util.List;

import tickets.client.ModelFacade;
import tickets.common.DestinationCard;
import tickets.common.IObserver;
// What info do we need?

public class DestinationPresenter implements IDestinationPresenter {
	public List<DestinationCard> getDestinationCards(){
		return ModelFacade.getInstance().getLocalPlayer().getDestinationCardOptions();
	}
	public void chooseDestinationCards(DestinationCard toDiscard){
		ModelFacade.getInstance().discardDestinationCard(toDiscard);
	}
}