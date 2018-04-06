package tickets.client.gui.presenters;

import android.util.Log;

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
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.TrainCardWrapper;

public class GameMapPresenter implements IGameMapPresenter {

    private IObservable observable;
    private IHolderGameMapFragment holder;
    private ITaskManager manager;

    public GameMapPresenter(IHolderGameMapFragment setHolder) {
        holder = setHolder;
        manager = AsyncManager.getInstance();
        ClientFacade.getInstance().linkObserver(this);
    }

    @Override
    public List<Route> getClaimedRoutes() {
        return ClientFacade.getInstance().getClaimedRoutes();
    }

    @Override
    public void claimRoute(Route route) {
        if (route.isDouble() || route.getFirstColor() == RouteColors.Gray) {
            List<String> colors = ClientFacade.getInstance().getPossibleColorsForRoute(route);
            if (colors.size() == 0) {
                holder.toastMessage("You do not have enough resources to claim this route.");
                holder.clearSelectedCities();
            }
            else holder.displayChooseColorDialog(colors);
        }
        else {
            holder.toastMessage("Claiming route " + route.toString());

            List<TrainCard> cards = ClientFacade.getInstance().getCards(route.getLength(), route.getFirstColor().toString());
            if (cards == null) {
                holder.toastMessage("You do not have enough resources to claim this route.");
                return;
            }
            manager.claimRoute(route, new TrainCardWrapper(cards));
        }
    }

    @Override
    public void claimRoute(Route route, String color) {
        holder.toastMessage("Claiming route " + route.toString() + " with " + color.toLowerCase() + " resources.");
        List<TrainCard> cards = ClientFacade.getInstance().getCards(route.getLength(), color);
        if (cards == null) {
            holder.toastMessage("You do not have enough resources to claim this route.");
            return;
        }
        manager.claimRoute(route, new TrainCardWrapper(cards));
    }

    @Override
    public List<Route> getAllRoutes() {
        return ClientFacade.getInstance().getAllRoutes();
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
                Log.d("Drawing", "checkUpdate has been called on GameMapPresenter");
                holder.checkUpdate();
                break;
            default:
                break;
        }
        return;
    }

    @Override
    public void setObservable(IObservable observable) {
        this.observable = observable;
    }
}
