
package tickets.client.gui.presenters;

import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ModelFacade;


public class GamePresenter implements IGamePresenter {
  private IObservable observable;
  private IHolderActivity holder;

  public GamePresenter(IHolderActivity setHolder) {
    holder = setHolder;
    ModelFacade.getInstance().linkObserver(this);
  }

  public void takeTurn() {
  	return;
  }

    // from IObserver
  public void notify(IMessage state) {
    return;
  }
  
  public void setObservable(IObservable setObservable) {
  	observable = setObservable;
  	return;
  }
}