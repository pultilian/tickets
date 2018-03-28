package tickets.client.gui.presenters;

import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.ITaskManager;
import tickets.client.async.AsyncManager;
import tickets.common.ClientModelUpdate;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.Route;
import tickets.common.TrainCard;

public class GameMapPresenter implements IGameMapPresenter {

    private IObservable observable;
    private IHolderActivity holder;
    private ITaskManager manager;

    public GameMapPresenter(IHolderActivity setHolder) {
        holder = setHolder;
        manager = AsyncManager.getInstance();
        ClientFacade.getInstance().linkObserver(this);
    }

    @Override
    public List<Route> getClaimedRoutes() {
        return ClientFacade.getInstance().getGame().getClaimedRoutes();
    }

    @Override
    public void claimRoute(Route route) {
        List<TrainCard> cards = ClientFacade.getInstance().getCardsForRoute(route);
        if (cards == null) {
            holder.toastMessage("You do not have enough resources to claim that route");
            return;
        }
        manager.claimRoute(route, cards);
    }


    @Override
    public void notify(IMessage state) {
        if (state.getClass() == ClientStateChange.class) {
            //do nothing
        } else if (state.getClass() == ClientModelUpdate.class) {
            checkUpdate((ClientModelUpdate.ModelUpdate) state.getMessage());
        } else if (state.getClass() == ExceptionMessage.class) {
            Exception e = (Exception) state.getMessage();
            if (holder != null)
                holder.toastException(e);
            else
                System.out.println(e.getMessage());
        } else {
            Exception err = new Exception("Observer err: invalid IMessage of type " + state.getClass());
            holder.toastException(err);
        }
    }

    private void checkUpdate(ClientModelUpdate.ModelUpdate update) {
        switch(update) {
            case mapUpdated:
                //TODO: update map visually
                break;
            default:
                break;
        }
    }

    @Override
    public void setObservable(IObservable observable) {
        this.observable = observable;
    }
}
