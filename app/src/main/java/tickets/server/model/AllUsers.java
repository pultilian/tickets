package tickets.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tickets.common.UserData;

public class AllUsers {

    private static AllUsers INSTANCE = null;

    public static AllUsers getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AllUsers();
        }
        return INSTANCE;
    }

    private List<UserData> users;

    private AllUsers(){
        users = new ArrayList<>();
    }

    public boolean userExists(String username) {
        for (UserData user: users){
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }

    public boolean verifyLogin(String username, String password){
        for (UserData user: users){
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) return true;
        }
        return false;
    }

    public String getUsername(String authToken){
        for (UserData user : users){
            if (user.getAuthenticationToken().equals(authToken)) return user.getUsername();
        }
        return null;
    }

    public String getAuthToken(String username){
        for (UserData user : users){
            if (user.getUsername().equals(username)) return user.getAuthenticationToken();
        }
        return null;
    }

    public String addUser(UserData user){
        String newAuthToken = UUID.randomUUID().toString();
        user.setAuthenticationToken(newAuthToken);
        users.add(user);
        return newAuthToken;
    }
}
