package tickets.server.dataaccess.interfaces;

import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public interface PlayerDataAccess {
    public void addPlayer(String player, String gameID, String username) throws Exception;
    public List<String> getPlayers()throws Exception;
    public void removePlayers(String gameID, String username) throws Exception;
    public void clear() throws Exception;
    public void addDeltas(String command, String gameID, String username) throws Exception;
    public List<String> getDeltas(String gameID, String username) throws Exception;
}
