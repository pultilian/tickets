package tickets.client.gui.presenters;

import java.util.ArrayList;
import java.util.List;

import tickets.client.ClientFacade;
import tickets.common.PlayerSummary;

/**
 * Created by Pultilian on 3/23/2018.
 */

public class GameSummaryPresenter {
    public List<PlayerSummary> getSummary(){
        return ClientFacade.getInstance().getGameSummary();
    }
}
