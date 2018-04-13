package tickets.server.dataAccess.Factory;

import tickets.server.dataAccess.Interfaces.DAOFactory;
import tickets.server.dataAccess.Interfaces.GameDataAccess;
import tickets.server.dataAccess.Interfaces.LobbyDataAccess;
import tickets.server.dataAccess.Interfaces.PlayerDataAccess;
import tickets.server.dataAccess.Interfaces.UserDataAccess;
import tickets.server.dataAccess.RelationalDAO.GameDA_Rel;
import tickets.server.dataAccess.RelationalDAO.LobbyDA_Rel;
import tickets.server.dataAccess.RelationalDAO.PlayerDA_Rel;
import tickets.server.dataAccess.RelationalDAO.UserDA_Rel;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class RelationalFactory implements DAOFactory {
    private GameDataAccess gameDA;
    private LobbyDataAccess lobbyDA;
    private PlayerDataAccess playerDA;
    private UserDataAccess userDA;

    @Override
    public void createDAOs() throws Exception{
        gameDA = new GameDA_Rel();
        lobbyDA = new LobbyDA_Rel();
        playerDA = new PlayerDA_Rel();
        userDA = new UserDA_Rel();
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
