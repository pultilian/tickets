package tickets.file_dao;

import java.util.List;

import tickets.server.dataaccess.DataAccess;
import tickets.server.dataaccess.interfaces.LobbyDataAccess;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class LobbyDA_File extends DataAccess implements LobbyDataAccess {
    private FileAccess fileAccess;

    public LobbyDA_File() throws Exception {
        fileAccess = new FileAccess();
    }

    @Override
    public void addLobby(String lobby, String id) throws Exception {
        fileAccess.checkpointUpdate(lobby, "lobby", id);
    }

    @Override
    public List<String> getLobbies() throws Exception {
        return fileAccess.getAllObjects("lobby");
    }

    @Override
    public void removeLobbies(String lobbyID) throws Exception {
        fileAccess.removeFile("lobby", lobbyID);
    }

    @Override
    public void clear() throws Exception {
        fileAccess.removeAllFiles();
    }

    @Override
    public void addDeltas(String command, String lobbyID) throws Exception {
        fileAccess.addUpdate(command, "lobby", lobbyID);
    }

    @Override
    public List<String> getDeltas(String id) throws Exception {
        return fileAccess.getDeltasForObject("lobby", id);
    }
    public void clearDeltas() throws Exception{
        return;
    }
}
