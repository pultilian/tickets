
package tickets.client.gui.presenters;

import tickets.common.Lobby;

import tickets.client.model.observable.*;


public interface ILobbyPresenter extends IObserver {
  
  public void startGame(Lobby id);
  public void leaveLobby();
  public void addGuest();

  // from IObserver
  public void notify(IMessage state);
  public void setObservable(ClientObservable setObservable);
  
}
