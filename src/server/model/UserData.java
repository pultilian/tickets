package server.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserData {

    public static UserData SINGLETON = new UserData();

    private Map<String, String> usernamePassword;
    private Map<String, String> authTokenUsername;

    private UserData(){
        usernamePassword = new HashMap<>();
        authTokenUsername = new HashMap<>();
    }

    public boolean verifyLogin(String username, String password){
        if (usernamePassword.containsKey(username)){
            if (usernamePassword.get(username).equals(password)) return true;
        }

        return false;
    }

    public void addUser(String username, String password){
        usernamePassword.put(username, password);
    }

    public String createAuthToken(String username){
        String newAuthToken = UUID.randomUUID().toString();
        authTokenUsername.put(newAuthToken, username);
        return newAuthToken;
    }

    public String getUsername(String authToken){
        return authTokenUsername.get(authToken);
    }
}
