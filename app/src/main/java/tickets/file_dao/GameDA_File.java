package tickets.file_dao;

import java.util.List;

import tickets.server.dataaccess.DataAccess;
import tickets.server.dataaccess.interfaces.GameDataAccess;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class GameDA_File extends DataAccess implements GameDataAccess {
    private FileAccess fileAccess;

    public GameDA_File() throws Exception{
        fileAccess = new FileAccess();
    }
    @Override
    public void addGame(String game, String id) throws Exception {
        fileAccess.checkpointUpdate(game, "game", id);
    }

    @Override
    public List<String> getGames()throws Exception {
        return fileAccess.getAllObjects("game");
    }

    @Override
    public void removeGames(String gameID) throws Exception{
        fileAccess.removeFile("game", gameID);
    }

    @Override
    public void clear() throws Exception {
        fileAccess.removeAllFiles();
    }

    @Override
    public void addDeltas(String command, String gameID) throws Exception {
        fileAccess.addUpdate(command, "game", gameID);
    }

    @Override
    public List<String> getDeltas(String gameID) throws Exception {
        return fileAccess.getDeltasForObject("game", gameID);
    }
}
