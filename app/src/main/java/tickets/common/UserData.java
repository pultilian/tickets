package tickets.common;

public class UserData {
	private String username;
	private String password;

	private String authenticationToken;

	
	public UserData(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean checkValues() {
	    for (char c : username.toCharArray()) {
	        if (!Character.isLetterOrDigit(c)) {
	            return false;
            }
        }
        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}
	
	public void setAuthenticationToken(String token) {
		this.authenticationToken = token;
	}

}
