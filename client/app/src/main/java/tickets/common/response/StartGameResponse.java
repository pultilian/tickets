package tickets.common.response;

public class StartGameResponse extends Response {

    private String gameID;

    public StartGameResponse(Exception exception){
        super(exception);
    }

    public StartGameResponse(String gameID){
        this.gameID = gameID;
    }

    public String getGameID(){ return gameID; }
}
