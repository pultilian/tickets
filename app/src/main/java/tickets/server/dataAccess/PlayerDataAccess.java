package tickets.server.dataAccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class PlayerDataAccess extends DataAccess {
    public PlayerDataAccess() throws Exception {

    }

    public void addPlayer(String player, String gameID, String username) throws Exception {
        openConnection();
        String insert = "insert into Player values (?, ?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, username);
        statement.setString(2, gameID);
        statement.setString(3, player);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public List<String> getPlayers()throws Exception {
        openConnection();
        List<String> newPlayers = null;
        String query = "select player from Player";
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String jsonString = results.getString(1);
            newPlayers.add(jsonString);
        }
        results.close();
        statement.close();
        closeConnection();
        return newPlayers;
    }

    public void removePlayers(String gameID) throws Exception{
        openConnection();
        String delete = "delete from Player \n" +
                "where exists ( \n" +
                "select * \n" +
                "from Player p1 \n" +
                "Player.game_id = ? \n" +
                "and Player.username = ?) \n";
        statement = connection.prepareStatement(delete);
        statement.setString(1, gameID);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public void clear() throws Exception {
        openConnection();
        String clear = "delete from Player";
        statement = connection.prepareStatement(clear);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }
    public void addDeltas(String command, String gameID, String username) throws Exception {
        openConnection();
        String insert = "insert into PlayerDeltas values (?, ?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, username);
        statement.setString(2, gameID);
        statement.setString(3, command);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public List<String> getDeltas() throws Exception {
        openConnection();
        List<String> newDeltas = null;
        String query = "select deltas from PlayerDeltas";
        statement = connection.prepareStatement(query);
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
