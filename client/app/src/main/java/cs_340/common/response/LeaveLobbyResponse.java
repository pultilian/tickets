package cs_340.common.response;

public class LeaveLobbyResponse extends Response {

    private String message;

    public LeaveLobbyResponse(Exception exception){
        super(exception);
    }

    public LeaveLobbyResponse(String message){
        this.message = message;
    }

    public String getMessage(){ return message; }
}
