package tickets.server.dataAccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class UserDataAccess extends DataAccess {
    public UserDataAccess() throws Exception {

    }
    
    public void addUserData(String userData, String authToken) throws Exception {
        openConnection();
        String insert = "insert into UserData values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, authToken);
        statement.setString(2, userData);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public List<String> getUserData()throws Exception {
        openConnection();
        List<String> newUserData = null;
        String query = "select userData from UserData";
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String jsonString = results.getString(1);
            newUserData.add(jsonString);
        }
        results.close();
        statement.close();
        closeConnection();
        return newUserData;
    }

    public void removeUserData(String authToken) throws Exception{
        openConnection();
        String delete = "delete from UserData where id = ?";
        statement = connection.prepareStatement(delete);
        statement.setString(1, authToken);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public void clear() throws Exception {
        openConnection();
        String clear = "delete from UserData";
        statement = connection.prepareStatement(clear);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }
}
