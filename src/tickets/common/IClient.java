package tickets.common;

public interface IClient {

    // FOR CLIENTS IN LOBBY LIST
    public void addLobbyToList(Lobby lobby);
    public void removeLobbyFromList(Lobby lobby);
    public void addPlayersToLobbyInList(Lobby lobby, int numToAdd);
    public void removePlayersFromLobbyInList(Lobby lobby, int numToRemove);

    // FOR CLIENTS IN A LOBBY
    public void addPlayer(Player player);
    public void removePlayer(Player player);
    public void startGame();

    // FOR CLIENTS IN A GAME
    public void endCurrentTurn();
}
