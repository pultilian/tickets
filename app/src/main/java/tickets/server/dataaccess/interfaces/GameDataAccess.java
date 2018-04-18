package tickets.server.dataaccess.interfaces;

import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public interface GameDataAccess {

    public void addGame(String game, String id) throws Exception;
    public List<String> getGames()throws Exception;
    public void removeGames(String gameID) throws Exception;
    public void clear() throws Exception;
    public void addDeltas(String command, String gameID) throws Exception;
    public List<String> getDeltas(String gameID) throws Exception;
    public void clearDeltas() throws Exception;

}
