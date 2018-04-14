package tickets.server.dataAccess.RelationalDAO;

import java.sql.ResultSet;
import java.util.List;

import tickets.server.dataAccess.DataAccess;
import tickets.server.dataAccess.Interfaces.GameDataAccess;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class GameDA_Rel extends DataAccess implements GameDataAccess {

    public GameDA_Rel()throws Exception {

    }

    @Override
    public void addGame(String game, String id) throws Exception {
        openConnection();
        String insert = "insert into Game values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, id);
        statement.setString(2, game);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public List<String> getGames()throws Exception {
        openConnection();
        List<String> newGames = null;
        String query = "select game from Game";
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String jsonString = results.getString(1);
            newGames.add(jsonString);
        }
        results.close();
        statement.close();
        closeConnection();
        return newGames;
    }

    @Override
    public void removeGames(String gameID) throws Exception{
        openConnection();
        String delete = "delete from Game where id = ?";
        statement = connection.prepareStatement(delete);
        statement.setString(1, gameID);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public void clear() throws Exception {
        openConnection();
        String clear = "delete from Game";
        statement = connection.prepareStatement(clear);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public void addDeltas(String command, String gameID) throws Exception {
        openConnection();
        String insert = "insert into GameDeltas values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, gameID);
        statement.setString(2, command);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public List<String> getDeltas(String gameID) throws Exception {
        openConnection();
        List<String> newDeltas = null;
        String query = "select deltas from GameDeltas where id = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1,gameID);
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
