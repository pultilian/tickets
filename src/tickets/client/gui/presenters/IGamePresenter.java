
package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;


public interface IGamePresenter extends IObserver {

    public void takeTurn();

    // from IObserver
    void notify(IMessage state);
}