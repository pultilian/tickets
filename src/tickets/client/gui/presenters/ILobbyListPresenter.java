
package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;

public interface ILobbyListPresenter extends IObserver {
  
    public void createLobby(Lobby lobby);

    public void joinLobby(String id);
    public void joinLobbyCallback(boolean success);

    public void logout();
    public void loginCallback(boolean success);

    // from IObserver
    public void notify(IMessage state);
  
}
