package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.IObserver;
import tickets.common.Route;

public interface IGameMapPresenter extends IObserver {
    List<Route> getClaimedRoutes();

    void claimRoute(Route route);

    List<Route> getAllRoutes();
}
