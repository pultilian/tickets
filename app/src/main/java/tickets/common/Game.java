package tickets.common;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String gameId;
    private List<String> chat;
    private List<String> gameHistory;

    public Game(String gameId){
        this.gameId = gameId;
        chat = new ArrayList<>();
        gameHistory = new ArrayList<>();
    }

    public String getGameId(){ return gameId; }
    public List<String> getChat(){ return chat; }
    public List<String> getGameHistory(){ return gameHistory; }

    public void addToChat(String message){ chat.add(message); }
    public void addToHistory(String message){ gameHistory.add(message); }
}
