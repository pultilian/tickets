package cs_340.client.gui.presenters;

import cs_340.client.model.observable.*;

public interface ILobbyListPresenter extends IObserver {
    void notify(IStateChange state);
    void updateObservable(IStateChange state);
}