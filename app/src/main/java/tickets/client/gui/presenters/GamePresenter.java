
package tickets.client.gui.presenters;

import java.util.List;

import tickets.client.ITaskManager;
import tickets.client.TaskManager;
import tickets.client.ClientFacade;
import tickets.client.async.AsyncManager;
import tickets.common.ClientModelUpdate;
import tickets.common.ClientStateChange;
import tickets.common.DestinationCard;
import tickets.common.ExceptionMessage;
import tickets.common.HandTrainCard;
import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.Player;
import tickets.common.TrainCard;


public class GamePresenter implements IGamePresenter {
    private IObservable observable;
    private IHolderGameActivity holder;
    private ITaskManager manager;

    public GamePresenter(IHolderGameActivity setHolder) {
        holder = setHolder;
        ClientFacade.getInstance().linkObserver(this);
        manager = AsyncManager.getInstance();
    }

    public GamePresenter() {
        holder = null;
        ClientFacade.getInstance().linkObserver(this);
        manager = TaskManager.getInstance();
    }

    public void takeTurn() {
        return;
    }

    public void addToChat(String message) {
        manager.addToChat(message);
    }

    public List<TrainCard> getFaceUpCards(){
      return ClientFacade.getInstance().getGame().getFaceUpCards();
    }

    public Player getCurrentPlayer(){
        return ClientFacade.getInstance().getLocalPlayer();
    }

    public void drawTrainCard() {
        manager.drawTrainCard();
    }

    public void drawFaceUpTrainCard(int position) {
        manager.drawFaceUpCard(position);
    }

    public void drawDestinationCard() {
        manager.drawDestinationCard();
    }

    public void claimPath() {

    }

    public HandTrainCard getPlayerHand() {
        return ClientFacade.getInstance().getLocalPlayer().getHandTrainCards();
    }

    public void chooseDestinationCards(DestinationCard toDiscard) {

    }

    // from IObserver
    @Override
    public void notify(IMessage state) {
        if (state.getClass() == ClientStateChange.class) {

        }
        else if (state.getClass() == ClientModelUpdate.class) {
            ClientModelUpdate.ModelUpdate flag = (ClientModelUpdate.ModelUpdate) state.getMessage();
            checkClientModelUpdateFlag(flag);
        } else if (state.getClass() == ExceptionMessage.class) {
            Exception e = (Exception) state.getMessage();
            if (holder != null)
                holder.toastException(e);
            else
                System.err.println(e.getMessage());
        } else {
            Exception err = new Exception("Observer err: invalid IMessage of type " + state.getClass());
            if (holder != null)
                holder.toastException(err);
            else
                System.err.println(err.getMessage());
        }
        return;
    }

//----------------------------------------------------------------------------
//  private methods

    private void checkClientModelUpdateFlag(ClientModelUpdate.ModelUpdate flag) {
        switch (flag) {
            case playerTrainHandUpdated:
                holder.updatePlayerTrainHand();
                break;
            case faceUpCardUpdated:
                holder.updateFaceUpCards();
                break;
            case playerDestHandUpdated:
                holder.updatePlayerDestHand();
                break;
            case destCardOptionsUpdated:
                break;
            case gameHistoryUpdated:
                break;
            case playerInfoUpdated:
                break;
            case chatUpdated:
                break;
            case mapUpdated:
                break;
            default:
                Exception err = new Exception("Observer err: invalid Transition " + flag.name());
                holder.toastException(err);
                break;
        }
    }

    public void setObservable(IObservable setObservable) {
        observable = setObservable;
        return;
    }

    public List<DestinationCard> getPlayerDestinations() {
        return ClientFacade.getInstance().getLocalPlayer().getHandDestinationCards().getAllCards();
    }

    public void discardDestination(DestinationCard discard) {
        // not implemented
        return;
    }
}