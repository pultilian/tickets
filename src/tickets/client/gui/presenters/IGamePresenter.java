
package tickets.client.gui.presenters;

import tickets.client.model.observable.*;


public interface IGamePresenter extends IObserver {

    public void takeTurn();

    // from IObserver
    void notify(IMessage state);
    void setObservable(ClientObservable setObservable);
}