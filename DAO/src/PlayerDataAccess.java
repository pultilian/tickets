import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Player;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class PlayerDataAccess extends DataAccess {
    public PlayerDataAccess() throws Exception {

    }

    public String objectToJSON(Player request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Player JSONToObject(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Player.class);
    }

    public void addPlayer(Player player) throws Exception {
        openConnection();
        String insert = "insert into Player values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, player.getName());
        String addedPlayer = objectToJSON(player);
        statement.setString(2, addedPlayer);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public List<Player> getPlayers()throws Exception {
        openConnection();
        List<Player> newPlayers = null;
        String query = "select player from Player";
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String jsonString = results.getString(1);
            Player player = JSONToObject(jsonString);
            newPlayers.add(player);
        }
        results.close();
        statement.close();
        closeConnection();
        return newPlayers;
    }

    public void removePlayers(String playerID) throws Exception{
        openConnection();
        String delete = "delete from Player where id = ?";
        statement = connection.prepareStatement(delete);
        statement.setString(1, playerID);
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
}
