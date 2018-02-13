
package tickets.client.gui.presenters;

import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;


public interface IGamePresenter extends IObserver {

    public void takeTurn();

    // from IObserver
    void notify(IMessage state);
    void setObservable(IObservable setObservable);
}