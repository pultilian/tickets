
package tickets.client.gui.presenters;

import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.IObserver;
import tickets.common.Lobby;


public interface ILobbyPresenter extends IObserver {

	public Lobby getLobby();
  
  public void startGame(String lobbyID);
  public void leaveLobby(String lobbyID);

  // from IObserver
  public void notify(IMessage state);
  public void setObservable(IObservable setObservable);
  
}
