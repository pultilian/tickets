
package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.PlayerInfo;
import tickets.common.Route;
import tickets.common.TrainCard;
// What info do we need?

public interface IGameInfoPresenter {
	public List<String> getGameHistory();
	public List<PlayerInfo> getPlayerInfo();
	public int getCurrentTurn();
}