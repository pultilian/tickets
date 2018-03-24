package tickets.client.gui.presenters;

import java.util.ArrayList;
import java.util.List;

import tickets.client.ClientFacade;
import tickets.common.PlayerInfo;

public class GameInfoPresenter implements IGameInfoPresenter{
    public List<String> getGameHistory(){
        return ClientFacade.getInstance().getGame().getGameHistory();
    }
    public List<PlayerInfo> getPlayerInfo(){
        return new ArrayList<PlayerInfo>(ClientFacade.getInstance().getGame().getAllPlayers());
    }

    public int getCurrentTurn(){
        return ClientFacade.getInstance().getGame().getCurrentTurn();
    }
}
