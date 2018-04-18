package tickets.server.dataaccess.interfaces;

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
