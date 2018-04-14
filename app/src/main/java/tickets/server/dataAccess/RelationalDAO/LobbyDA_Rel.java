package tickets.server.dataAccess.RelationalDAO;

import java.sql.ResultSet;
import java.util.List;

import tickets.server.dataAccess.DataAccess;
import tickets.server.dataAccess.Interfaces.LobbyDataAccess;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class LobbyDA_Rel extends DataAccess implements LobbyDataAccess {

    public LobbyDA_Rel() throws Exception {

    }

    @Override
    public void addLobby(String lobby, String id) throws Exception {
        openConnection();
        String insert = "insert into lobbies values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, id);
        statement.setString(2, lobby);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public List<String> getLobbies()throws Exception {
        openConnection();
        List<String> newLobbies = null;
        String query = "select lobbies from Lobby";
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String jsonString = results.getString(1);
            newLobbies.add(jsonString);
        }
        results.close();
        statement.close();
        closeConnection();
        return newLobbies;
    }

    @Override
    public void removeLobbies(String lobbyID) throws Exception{
        openConnection();
        String delete = "delete from lobbies where id = ?";
        statement = connection.prepareStatement(delete);
        statement.setString(1, lobbyID);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public void clear() throws Exception {
        openConnection();
        String clear = "delete from lobbies";
        statement = connection.prepareStatement(clear);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public void addDeltas(String command, String lobbyID) throws Exception {
        openConnection();
        String insert = "insert into lobbydeltas values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, lobbyID);
        statement.setString(2, command);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public List<String> getDeltas(String id) throws Exception {
        openConnection();
        List<String> newDeltas = null;
        String query = "select deltas from lobbydeltas where id = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1,id);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String jsonString = results.getString(1);
            newDeltas.add(jsonString);
        }
        results.close();
        statement.close();
        closeConnection();
        return newDeltas;
    }
}
