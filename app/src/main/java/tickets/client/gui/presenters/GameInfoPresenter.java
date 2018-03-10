package tickets.client.gui.presenters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tickets.client.ModelFacade;
import tickets.common.Player;
import tickets.common.PlayerInfo;

/**
 * Created by Pultilian on 3/9/2018.
 */

public class GameInfoPresenter implements IGameInfoPresenter{
    public List<String> getGameHistory(){
        return ModelFacade.getInstance().getGame().getGameHistory();
    }
    public List<PlayerInfo> getPlayerInfo(){
        return new ArrayList<PlayerInfo>(ModelFacade.getInstance().getGame().getAllPlayers());
    }
}
