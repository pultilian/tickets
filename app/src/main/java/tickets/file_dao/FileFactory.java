package tickets.file_dao;

import tickets.server.dataaccess.interfaces.DAOFactory;
import tickets.server.dataaccess.interfaces.GameDataAccess;
import tickets.server.dataaccess.interfaces.LobbyDataAccess;
import tickets.server.dataaccess.interfaces.PlayerDataAccess;
import tickets.server.dataaccess.interfaces.UserDataAccess;

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
