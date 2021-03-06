package tickets.common;

public interface IClient {

    // FOR CLIENTS IN LOBBY LIST
    public void addLobbyToList(Lobby lobby);
    public void removeLobbyFromList(Lobby lobby);
    public void addPlayerToLobbyInList(Lobby lobby, Player playerToAdd);
    public void removePlayerFromLobbyInList(Lobby lobby, Player player);

    // FOR CLIENTS IN A LOBBY
    public void removePlayer(Player player);
    public void startGame(Game game, HandTrainCard playerHand, ChoiceDestinationCards destCardOptions);

    // FOR CLIENTS IN A GAME
    // public void startTurn();
    public void endCurrentTurn();
    public void addChatMessage(String message);
    public void addToGameHistory(String message);
    public void addPlayerTrainCard();
    public void addClaimedRoute(Route route, RouteColors routeColor, PlayerColor player);
    public void addPlayerDestinationCards(String playerName, Integer numCards);
    public void replaceFaceUpCard(Integer position, TrainCard card);
    public void displayEndGame(GameSummary gameSummary);
}
