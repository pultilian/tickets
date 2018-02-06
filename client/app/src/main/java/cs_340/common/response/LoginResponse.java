package cs_340.common.response;

public class LoginResponse extends Response {

    private String message;
    private String authToken;

    public LoginResponse(Exception exception){
        super(exception);
    }

    public LoginResponse(String message, String authToken){
        this.message = message;
        this.authToken = authToken;
    }

    public String getMessage(){ return message; }

    public String getAuthToken(){ return authToken; }
}
