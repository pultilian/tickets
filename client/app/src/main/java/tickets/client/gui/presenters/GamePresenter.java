
package tickets.client.gui.presenters;

import tickets.client.model.observable.*;
import tickets.client.ModelFacade;

public class GamePresenter implements IGamePresenter {
    private ClientObservable observable;
    private IHolderActivity holder;

    public GamePresenter(IHolderActivity setHolder) {
        holder = setHolder;
        ModelFacade.getInstance().linkObserver(this);
    }

    public void takeTurn() {
        return;
    }

    // from IObserver
    public void notify(IMessage state) {
        return;
    }

    public void setObservable(ClientObservable setObservable) {
        observable = setObservable;
        return;
    }
}