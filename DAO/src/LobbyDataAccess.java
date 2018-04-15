import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Lobby;
import common.Lobby;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class LobbyDataAccess extends DataAccess{

    public LobbyDataAccess() throws Exception {

    }

    public String objectToJSON(Lobby request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Lobby JSONToObject(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Lobby.class);
    }

    public void addLobby(Lobby lobby) throws Exception {
        openConnection();
        String insert = "insert into Lobby values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, lobby.getId());
        String addedLobby = objectToJSON(lobby);
        statement.setString(2, addedLobby);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public List<Lobby> getLobbies()throws Exception {
        openConnection();
        List<Lobby> newLobbies = null;
        String query = "select lobby from Lobby";
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String jsonString = results.getString(1);
            Lobby lobby = JSONToObject(jsonString);
            newLobbies.add(lobby);
        }
        results.close();
        statement.close();
        closeConnection();
        return newLobbies;
    }

    public void removeLobbies(String lobbyID) throws Exception{
        openConnection();
        String delete = "delete from Lobby where id = ?";
        statement = connection.prepareStatement(delete);
        statement.setString(1, lobbyID);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public void clear() throws Exception {
        openConnection();
        String clear = "delete from Lobby";
        statement = connection.prepareStatement(clear);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }
}
