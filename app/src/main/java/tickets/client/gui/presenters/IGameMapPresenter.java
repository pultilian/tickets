
package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.Route;

public interface IGameMapPresenter {
	// This isn't the final return value yet
	public List<Object> getClaimedRoutes();
	
	// No route object created yet - this route is
	// 	 a stub class within the client.gui.presenters
	//   package; the real route will need to be
	//   imported once it's written
	public void claimRoute(Route route);
}