
package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;

public interface ILobbyListPresenter extends IObserver {
  
  public void joinLobby();
  public void joinLobbyResponse();

  public void createLobby();
  public void createLobbyResponse();

  public void login();
  public void loginResponse();

  // from IObserver
  public void notify(IMessage state);
  
}
