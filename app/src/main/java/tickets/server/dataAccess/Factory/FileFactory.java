package tickets.server.dataAccess.Factory;

import tickets.server.dataAccess.Interfaces.DAOFactory;
import tickets.server.dataAccess.Interfaces.GameDataAccess;
import tickets.server.dataAccess.Interfaces.LobbyDataAccess;
import tickets.server.dataAccess.Interfaces.PlayerDataAccess;
import tickets.server.dataAccess.Interfaces.UserDataAccess;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class FileFactory implements DAOFactory {
    private GameDataAccess gameDA;
    private LobbyDataAccess lobbyDA;
    private PlayerDataAccess playerDA;
    private UserDataAccess userDA;

    @Override
    public void createDAOs() throws Exception{

    }

    @Override
    public LobbyDataAccess getLobbyDA(){
        return this.lobbyDA;
    }

    @Override
    public GameDataAccess getGameDA(){
        return this.gameDA;
    }

    @Override
    public PlayerDataAccess getPlayerDA(){
        return this.playerDA;
    }

    @Override
    public UserDataAccess getUserDA(){
        return this.userDA;
    }
}
