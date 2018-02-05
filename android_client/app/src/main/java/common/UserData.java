package common;

public class UserData {
    private String authenticationToken;
	private String username;
	private String password;

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String token) {
        this.authenticationToken = token;
    }

    //@pre  setUsername != null
    //
    //@post if setUsername contains only digits and letters, it is set aws the username and 'true' is returned
    //@post if setUsername contains any character that is not alphanumeric, username is unchanged and 'false' is returned
    public boolean setUsername(String setUsername) {
        for (char c : setUsername.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }

        username = setUsername;
        return true;
    }

    //@pre  setPassword != null
    //
    //@post if setPassword contains only digits, letters, and symbols. it is set as the password and 'true' is returned
    //@post if setPassword contains any character that is not alphanumeric or an expected symbol, password is unchanged and 'false' is returned
    public boolean setPassword(String setPassword) {
        for (char c : setPassword.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !isSymbolChar(c)) {
                return false;
            }
        }

        password = setPassword;
        return true;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    private boolean isSymbolChar(char c) {
        switch(c) {
            case '$':
            case '#':
            case '@':
            case '!':
            case '%':
            case '*':
            case '(':
            case ')':
                return true;
            default:
                return false;
        }
    }
}
