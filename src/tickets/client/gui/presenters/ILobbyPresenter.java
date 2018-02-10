
package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;

public interface ILobbyPresenter implements IObserver {
  
  public void startGame();
  public void startGameResponse();

  public void leaveLobby();
  public void leaveLobbyResponse();

  public void addGuest();
  public void addGuestResponse();

  // from IObserver
  public void notify(IMessage state);
  
}
