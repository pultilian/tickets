package tickets.common;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String gameId;
    private String name;
    private List<String> chat;
    private List<String> gameHistory;
    private int currentTurn;
    private List<PlayerInfo> gamePlayers;
    private List<TrainCard> faceUpCards;
    private GameMap map;

    public Game(String gameId, String name){
        this.gameId = gameId;
        this.name = name;
        chat = new ArrayList<>();
        gameHistory = new ArrayList<>();
        gamePlayers = new ArrayList<>();
        currentTurn = 0;
        faceUpCards = new ArrayList<>();
    }

    //----------------------------------------------------------------------------------------------
    // The server can use these setters to convert from a ServerGame to a Game.

    public void setChat(List<String> chat) {
        this.chat = chat;
    }

    public void setGameHistory(List<String> gameHistory) {
        this.gameHistory = gameHistory;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    //----------------------------------------------------------------------------------------------

    public void initializeMap() {
        map = new GameMap();
    }

    public String getGameId(){
        return gameId;
    }

    public String getName() {
        return name;
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

    public PlayerInfo getPlayerInfo(String playerName) {
        for (PlayerInfo player : gamePlayers) {
            if (player.getName().equals(playerName)) return player;
        }
        return null;
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
        getActivePlayerInfo().addToScore(route.getPointValue());
        getActivePlayerInfo().useShips(route.getLength());
        getActivePlayerInfo().useTrainCards(route.getLength());
    }

    public List<Route> getClaimedRoutes() {
        return map.getClaimedRoutes();
    }

    public List<Route> getAllRoutes() {
        return map.getAllRoutes();
    }

    public void addTrainCardToActivePlayer(){
        getActivePlayerInfo().addTrainCard();
    }
}
