import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.UserData;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class UserDataAccess extends DataAccess {
    public UserDataAccess() throws Exception {

    }

    public String objectToJSON(UserData request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public UserData JSONToObject(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, UserData.class);
    }

    public void addUserData(UserData userData) throws Exception {
        openConnection();
        String insert = "insert into UserData values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, userData.getUsername());
        String addedUserData = objectToJSON(userData);
        statement.setString(2, addedUserData);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public List<UserData> getUserDatas()throws Exception {
        openConnection();
        List<UserData> newUserDatas = null;
        String query = "select userData from UserData";
        statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String jsonString = results.getString(1);
            UserData userData = JSONToObject(jsonString);
            newUserDatas.add(userData);
        }
        results.close();
        statement.close();
        closeConnection();
        return newUserDatas;
    }

    public void removeUserDatas(String userDataID) throws Exception{
        openConnection();
        String delete = "delete from UserData where id = ?";
        statement = connection.prepareStatement(delete);
        statement.setString(1, userDataID);
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
