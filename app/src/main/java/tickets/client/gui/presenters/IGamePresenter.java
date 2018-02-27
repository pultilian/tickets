
package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.DestinationCard;
import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;
import tickets.common.TrainCard;

public interface IGamePresenter extends IObserver {

    public void takeTurn();
    public void addToChat(String message);
    public void drawTrainCard();
    public void drawFaceUpTrainCard(int position);
    public void drawDestinationCard();
    public void claimPath(); //this should take a parameter...
    public List<TrainCard> getPlayerHand();
    public void chooseDestinationCards(DestinationCard toDiscard);

    // from IObserver
    void notify(IMessage state);
    void setObservable(IObservable setObservable);
}