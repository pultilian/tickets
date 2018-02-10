package tickets.client.gui.presenters;

import tickets.client.model.observable.*;

public interface ILobbyListPresenter extends IObserver {
    void notify(IStateChange state);
    void updateObservable(IStateChange state);
}