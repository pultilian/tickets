package tickets.common;

import java.util.List;

public interface IClient {

    // FOR CLIENTS IN LOBBY LIST
    public void addLobbyToList(Lobby lobby);
    public void removeLobbyFromList(Lobby lobby);
    public void addPlayerToLobbyInList(Lobby lobby, Player playerToAdd);
    public void removePlayerFromLobbyInList(Lobby lobby, Player player);

    // FOR CLIENTS IN A LOBBY
    public void removePlayer(Player player);
    public void startGame(Game game, TrainCard[] initialCards, DestinationCard[] initialDestinationCards);

    // FOR CLIENTS IN A GAME
    // public void startTurn();
    public void endCurrentTurn();
    public void addChatMessage(String message);
    public void addToGameHistory(String message);
    public void addPlayerTrainCard(String playerID);
    public void givePlayerTrainCard(TrainCard card);
    public void givePlayerDestinationCards(List<DestinationCard> cards);
}
