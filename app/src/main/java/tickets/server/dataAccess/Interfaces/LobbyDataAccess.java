package tickets.server.dataAccess.Interfaces;

import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public interface LobbyDataAccess {

    public void addLobby(String lobby, String id) throws Exception;

    public List<String> getLobbies()throws Exception;

    public void removeLobbies(String lobbyID) throws Exception;

    public void clear() throws Exception;

    public void addDeltas(String command, String lobbyID) throws Exception;

    public List<String> getDeltas() throws Exception;
}
