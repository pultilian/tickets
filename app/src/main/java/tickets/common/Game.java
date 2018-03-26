package tickets.common;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String gameId;
    private List<String> chat;
    private List<String> gameHistory;
    private int currentTurn;
    private List<PlayerInfo> gamePlayers;
    private List<TrainCard> faceUpCards;
    private GameMap map;

    public Game(String gameId){
        this.gameId = gameId;
        chat = new ArrayList<>();
        gameHistory = new ArrayList<>();
        gamePlayers = new ArrayList<>();
        currentTurn = 0;
        faceUpCards = new ArrayList<>();
        map = new GameMap();
    }

    public String getGameId(){
        return gameId;
    }

    public List<String> getChat(){
        return chat;
    }

    public List<String> getGameHistory(){
        return gameHistory;
    }

    public void addToChat(String message){
        chat.add(message);
    }

    public void addToHistory(String message){
        gameHistory.add(message);
    }

    public void addPlayer(PlayerInfo info) {
        gamePlayers.add(info);
    }

    public PlayerInfo getActivePlayerInfo() {
        return gamePlayers.get(currentTurn);
    }

    public List<PlayerInfo> getAllPlayers() {
        return gamePlayers;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void nextTurn() {
        currentTurn = (currentTurn + 1) % gamePlayers.size();
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

    public void claimRoute(Route route, RouteColors routeColor, PlayerColor player) {
        map.claimRoute(route.getSrc(), route.getDest(), routeColor, player);
        getActivePlayerInfo().addToScore(route.getLength());
        getActivePlayerInfo().useShips(route.getLength());
        getActivePlayerInfo().useTrainCards(route.getLength());
    }
}
