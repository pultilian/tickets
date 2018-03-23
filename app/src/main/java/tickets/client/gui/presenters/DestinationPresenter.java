
package tickets.client.gui.presenters;

import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.ITaskManager;
import tickets.client.async.AsyncManager;
import tickets.common.DestinationCard;
// What info do we need?

public class DestinationPresenter implements IDestinationPresenter {
	private ITaskManager manager;

	public DestinationPresenter() {
	    manager = AsyncManager.getInstance();
    }

	public DestinationPresenter(ITaskManager manager){
	    this.manager = manager;
    }

	public List<DestinationCard> getDestinationCards(){
		return ClientFacade.getInstance().getLocalPlayer().getDestinationCardOptions();
	}

	public void chooseDestinationCards(DestinationCard toDiscard){
		manager.discardDestinationCard(toDiscard);
	}
}