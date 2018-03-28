package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.IObserver;
import tickets.common.Route;
import tickets.common.TrainCard;

public interface IGameMapPresenter extends IObserver {
    public List<Route> getClaimedRoutes();

    // No route object created yet - this route is
    // 	 a stub class within the client.gui.presenters
    //   package; the real route will need to be
    //   imported once it's written
    public void claimRoute(Route route, List<TrainCard> cards);
}
