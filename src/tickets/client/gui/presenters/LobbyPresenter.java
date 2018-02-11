
package tickets.client.gui.presenters;

import tickets.common.Lobby;

import tickets.client.model.observable.*;


public class LobbyPresenter implements IObserver {
  private ClientObservable observable;


  public void startGame(Lobby id) {
    return;
  }

  public void leaveLobby() {
    return;
  }

  public void addGuest() {
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
