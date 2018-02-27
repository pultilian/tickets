
package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.DestinationCard;
import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ModelFacade;
import tickets.common.TrainCard;


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

  public void addToChat(String message) { ModelFacade.getInstance().addToChat(message); }

  public void drawTrainCard() {

  }

  public void drawFaceUpTrainCard(int position) {

  }

  public void drawDestinationCard() {

  }

  public void claimPath() {

  }

  public List<TrainCard> getPlayerHand() {
    return null;
  }

  public void chooseDestinationCards(DestinationCard toDiscard) {

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