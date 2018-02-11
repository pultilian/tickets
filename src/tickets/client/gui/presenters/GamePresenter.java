
package tickets.client.gui.presenters;

import tickets.client.model.observable.*;

public class GamePresenter implements IGamePresenter {
  private ClientObservable observable;


  public void takeTurn() {
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