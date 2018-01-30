package common.response;

public class AddGuestResponse extends Response {

    private String message;
    private String guestID;

    public AddGuestResponse(Exception exception) {
        super(exception);
    }

    public AddGuestResponse(String message, String guestID){
        this.message = message;
        this.guestID = guestID;
    }

    public String getMessage(){ return message; }

    public String getGuestID(){ return guestID; }
}
