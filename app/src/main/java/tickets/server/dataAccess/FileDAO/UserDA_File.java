package tickets.server.dataAccess.FileDAO;

import java.util.List;

import tickets.server.dataAccess.DataAccess;
import tickets.server.dataAccess.Interfaces.UserDataAccess;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class UserDA_File extends DataAccess implements UserDataAccess {

    private FileAccess fileAccess;

    public UserDA_File() throws Exception{
        fileAccess = new FileAccess();
    }
    @Override
    public void addUserData(String userData, String authToken) throws Exception{
        fileAccess.checkpointUpdate(userData, "user", authToken);
    }
    @Override
    public List<String> getUserData()throws Exception{
        return fileAccess.getAllObjects("user");
    }
    @Override
    public void removeUserData(String authToken) throws Exception{
        fileAccess.removeFile("user", authToken);
    }
    @Override
    public void clear() throws Exception{
        fileAccess.removeAllFiles();
    }

}
