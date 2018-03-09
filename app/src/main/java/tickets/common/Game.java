package tickets.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private String gameId;
    private List<String> chat;
    private List<String> gameHistory;
    private Map<String, PlayerInfo> gamePlayers;
    private List<TrainCard> faceUpCards;

    public Game(String gameId){
        this.gameId = gameId;
        chat = new ArrayList<>();
        gameHistory = new ArrayList<>();
        gamePlayers = new HashMap<>();
        faceUpCards = new ArrayList<>();
    }

    public String getGameId(){ return gameId; }
    public List<String> getChat(){ return chat; }
    public List<String> getGameHistory(){ return gameHistory; }

    public void addToChat(String message){ chat.add(message); }
    public void addToHistory(String message){ gameHistory.add(message); }

    public void addPlayer(String playerId, PlayerInfo info) {
        gamePlayers.put(playerId, info);
    }

    public PlayerInfo getPlayerInfo(String playerId) {
        return gamePlayers.get(playerId);
    }

    public Map<String, PlayerInfo> getAllPlayers() {
        return gamePlayers;
    }

    public void setFaceUpCards(List<TrainCard> faceUpCards){
        this.faceUpCards = faceUpCards;
    }

    public void replaceFaceUpCard(int position, TrainCard faceUpCard){
        faceUpCards.set(position, faceUpCard);
    }

    public List<TrainCard> getFaceUpCards(){
        return faceUpCards;
    }
}
