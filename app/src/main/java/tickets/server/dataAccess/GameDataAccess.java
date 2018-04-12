package tickets.server.dataAccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class GameDataAccess extends DataAccess {

    public GameDataAccess() throws Exception {

    }

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

    public void removeGames(String gameID) throws Exception{
        openConnection();
        String delete = "delete from Game where id = ?";
        statement = connection.prepareStatement(delete);
        statement.setString(1, gameID);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public void clear() throws Exception {
        openConnection();
        String clear = "delete from Game";
        statement = connection.prepareStatement(clear);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }
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

    public List<String> getDeltas() throws Exception {
        openConnection();
        List<String> newDeltas = null;
        String query = "select deltas from GameDeltas";
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
