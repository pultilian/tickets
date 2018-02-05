package common.response;

public class StartGameResponse extends Response {

    private String gameID;
    private String playerID;
    private String playerColor;

    public StartGameResponse(Exception exception){
        super(exception);
    }

    public StartGameResponse(String gameID, String playerID, String playerColor){
        this.gameID = gameID;
        this.playerID = playerID;
        this.playerColor = playerColor;
    }

    public String getGameID(){ return gameID; }

    public String getPlayerID(){ return playerID; }

    public String getPlayerColor(){ return playerColor; }
}
