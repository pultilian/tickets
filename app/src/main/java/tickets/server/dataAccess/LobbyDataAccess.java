package tickets.server.dataAccess;

import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class LobbyDataAccess extends DataAccess{

    public LobbyDataAccess() throws Exception {

    }
    public void addLobby(String lobby, String id) throws Exception {
        openConnection();
        String insert = "insert into Lobby values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, id);
        statement.setString(2, lobby);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public List<String> getLobbies()throws Exception {
        openConnection();
        List<String> newLobbies = null;
        String query = "select lobby from Lobby";
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

    public void addDeltas(String command, String lobbyID) throws Exception {
        openConnection();
        String insert = "insert into LobbyDeltas values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, lobbyID);
        statement.setString(2, command);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public List<String> getDeltas() throws Exception {
        openConnection();
        List<String> newDeltas = null;
        String query = "select deltas from LobbyDeltas";
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
