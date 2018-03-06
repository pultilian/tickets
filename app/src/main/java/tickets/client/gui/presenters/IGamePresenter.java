
package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.DestinationCard;
import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;
import tickets.common.TrainCard;

public interface IGamePresenter extends IObserver {

    // public void takeTurn();

    //----------------------------------------
    // Game Setup
    public void chooseDestinationCards(DestinationCard toDiscard);
        // this shouldn't be useful

    //----------------------------------------
    // Turn actions
    public void drawTrainCard();
    public void drawFaceUpTrainCard(int position);
    public void drawDestinationCard();
    public void discardDestination(DestinationCard discard);
        // can be used for setup or during a turn

    public void claimPath();
        // params?

    //----------------------------------------
    // Player data getters
    public List<DestinationCard> getPlayerDestinations();
    public List<TrainCard> getPlayerHand();


    // from IObserver
    void notify(IMessage state);
    void setObservable(IObservable setObservable);
}