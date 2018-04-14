package tickets.server.dataAccess.RelationalDAO;

import java.sql.ResultSet;
import java.util.List;

import tickets.server.dataAccess.DataAccess;
import tickets.server.dataAccess.Interfaces.PlayerDataAccess;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class PlayerDA_Rel extends DataAccess implements PlayerDataAccess {

    public PlayerDA_Rel() throws Exception{

    }

    @Override
    public void addPlayer(String player, String gameID, String username) throws Exception {
        openConnection();
        String insert = "insert into players values (?, ?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, username);
        statement.setString(2, gameID);
        statement.setString(3, player);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public List<String> getPlayers()throws Exception {
        openConnection();
        List<String> newPlayers = null;
        String query = "select player from players";
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

    @Override
    public void removePlayers(String gameID, String username) throws Exception{
        openConnection();
        String delete = "delete from players \n" +
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

    @Override
    public void clear() throws Exception {
        openConnection();
        String clear = "delete from players";
        statement = connection.prepareStatement(clear);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public void addDeltas(String command, String gameID, String username) throws Exception {
        openConnection();
        String insert = "insert into playerdeltas values (?, ?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, username);
        statement.setString(2, gameID);
        statement.setString(3, command);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public List<String> getDeltas(String gameID, String username) throws Exception {
        openConnection();
        List<String> newDeltas = null;
        String query = "select deltas from playerdeltas where game_id = ? and username = ? ";

        statement = connection.prepareStatement(query);
        statement.setString(1,gameID);
        statement.setString(2,username);
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
