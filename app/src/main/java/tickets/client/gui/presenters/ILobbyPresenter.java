
package tickets.client.gui.presenters;

import tickets.common.Lobby;
import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;


public interface ILobbyPresenter extends IObserver {

	public Lobby getLobby();
  
  public void startGame(String lobbyID);
  public void leaveLobby(String lobbyID);

  // from IObserver
  public void notify(IMessage state);
  public void setObservable(IObservable setObservable);
  
}
