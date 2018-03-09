
package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.ClientModelUpdate;
import tickets.common.DestinationCard;
import tickets.common.HandTrainCard;
import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;

import tickets.client.ModelFacade;
import tickets.common.Player;
import tickets.common.TrainCard;


public class GamePresenter implements IGamePresenter {
    private IObservable observable;
    private IHolderGameActivity holder;

    public GamePresenter(IHolderGameActivity setHolder) {
        holder = setHolder;
        ModelFacade.getInstance().linkObserver(this);
    }

    public void takeTurn() {
        return;
    }

    public void addToChat(String message) {
        ModelFacade.getInstance().addToChat(message);
    }

    public List<TrainCard> getFaceUpCards(){
      return  null; //ModelFacade.getInstance().getGame().getFaceUpCards();
    }

    public Player getCurrentPlayer(){
        return ModelFacade.getInstance().getLocalPlayer();
    }

    public void drawTrainCard() {
        ModelFacade.getInstance().drawTrainCard();
    }

    public void drawFaceUpTrainCard(int position) {
        ModelFacade.getInstance().drawFaceUpCard(position);
    }

    public void drawDestinationCard() {
        ModelFacade.getInstance().drawDestinationCard();
    }

    public void claimPath() {

    }

    public HandTrainCard getPlayerHand() {
        return ModelFacade.getInstance().getLocalPlayer().getHandTrainCards();
    }

    public void chooseDestinationCards(DestinationCard toDiscard) {

    }

    // from IObserver
    @Override
    public void notify(IMessage state) {
        if (state.getClass() == ClientStateChange.class) {
            ClientModelUpdate.ModelUpdate flag = (ClientModelUpdate.ModelUpdate) state.getMessage();
            checkClientModelUpdateFlag(flag);
        } else if (state.getClass() == ExceptionMessage.class) {
            Exception e = (Exception) state.getMessage();
            holder.toastException(e);
        } else {
            Exception err = new Exception("Observer err: invalid IMessage of type " + state.getClass());
            holder.toastException(err);
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
        ModelFacade.getInstance().getLocalPlayer().getHandDestinationCards();
        return null;
    }

    public void discardDestination(DestinationCard discard) {
        // not implemented
        return;
    }
}