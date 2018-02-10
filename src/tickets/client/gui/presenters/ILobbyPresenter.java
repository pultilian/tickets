
package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;

public interface ILobbyPresenter implements IObserver {
  
  public void startGame(Lobby id);
  public void startGameCallback();

  public void leaveLobby();
  public void leaveLobbyCallback();

  public void addGuest();
  public void addGuestCallback();

  // from IObserver
  public void notify(IMessage state);
  
}
