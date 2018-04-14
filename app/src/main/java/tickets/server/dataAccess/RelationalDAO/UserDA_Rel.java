package tickets.server.dataAccess.RelationalDAO;

import java.sql.ResultSet;
import java.util.List;

import tickets.server.dataAccess.DataAccess;
import tickets.server.dataAccess.Interfaces.UserDataAccess;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class UserDA_Rel extends DataAccess implements UserDataAccess {

    public UserDA_Rel() throws Exception {

    }

    @Override
    public void addUserData(String userData, String authToken) throws Exception {
        openConnection();
        String insert = "insert into users values (?, ?) ";
        statement = connection.prepareStatement(insert);
        statement.setString(1, authToken);
        statement.setString(2, userData);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public List<String> getUserData()throws Exception {
        openConnection();
        List<String> newUserData = null;
        String query = "select userData from users";
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

    @Override
    public void removeUserData(String authToken) throws Exception{
        openConnection();
        String delete = "delete from users where id = ?";
        statement = connection.prepareStatement(delete);
        statement.setString(1, authToken);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    @Override
    public void clear() throws Exception {
        openConnection();
        String clear = "delete from users";
        statement = connection.prepareStatement(clear);
        statement.executeUpdate();
        statement.close();
        closeConnection();
    }
}
