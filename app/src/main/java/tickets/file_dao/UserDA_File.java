package tickets.file_dao;

import java.util.List;

import tickets.common.database.FileAccess;
import tickets.server.dataaccess.DataAccess;
import tickets.server.dataaccess.interfaces.UserDataAccess;

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

    public void clearDeltas() throws Exception{
        return;
    }
}
