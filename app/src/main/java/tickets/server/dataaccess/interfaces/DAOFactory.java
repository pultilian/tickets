package tickets.server.dataAccess.Interfaces;

import tickets.server.dataAccess.Interfaces.GameDataAccess;
import tickets.server.dataAccess.Interfaces.LobbyDataAccess;
import tickets.server.dataAccess.Interfaces.PlayerDataAccess;
import tickets.server.dataAccess.Interfaces.UserDataAccess;

/**
 * Created by Pultilian on 4/11/2018.
 */

public interface DAOFactory {
    public void createDAOs() throws Exception;
    public LobbyDataAccess getLobbyDA();
    public GameDataAccess getGameDA();
    public PlayerDataAccess getPlayerDA();
    public UserDataAccess getUserDA();

}
