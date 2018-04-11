import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Game;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class GameDataAccess extends DataAccess {

    public GameDataAccess() throws Exception {

    }

    public String objectToJSON(Game request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Game JSONToObject(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Game.class);
    }

    public void addGame(Game game) throws Exception {
        openConnection();
        String insert = "insert into Game values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, game.getGameId());
        String addedGame = objectToJSON(game);
        statement.setString(2, addedGame);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public List<Game> getGames()throws Exception {
        openConnection();
        List<Game> newGames = null;
        String query = "select game from Game";
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String jsonString = results.getString(1);
            Game game = JSONToObject(jsonString);
            newGames.add(game);
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
}
