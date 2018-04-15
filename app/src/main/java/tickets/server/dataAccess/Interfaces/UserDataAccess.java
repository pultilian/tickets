package tickets.server.dataAccess.Interfaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public interface UserDataAccess {
    public void addUserData(String userData, String authToken) throws Exception;
    public List<String> getUserData()throws Exception;
    public void removeUserData(String authToken) throws Exception;
    public void clear() throws Exception;
}
