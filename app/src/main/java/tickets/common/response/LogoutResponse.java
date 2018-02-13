package tickets.common.response;

public class LogoutResponse extends Response {

    private String message;

    public LogoutResponse(Exception exception){
        super(exception);
    }

    public LogoutResponse(String message){
        this.message = message;
    }

    public String getMessage(){ return message; }
}
