package tickets.server;

import tickets.common.Command;
import tickets.common.IClient;
import tickets.common.Lobby;
import tickets.common.Player;

import java.util.ArrayDeque;
import java.util.Queue;

public class ClientProxy implements IClient {

    private String authToken;
    private Queue<Command> unprocessedCommands;

    public ClientProxy(String authToken){
        this.authToken = authToken;
        unprocessedCommands = new ArrayDeque<>();
    }

    public String getAuthToken(){ return authToken; }

    @Override
    public void addLobbyToList(Lobby lobby) {
        unprocessedCommands.add(new Command("addLobbyToList", new String[]{Lobby.class.getName()}, new Object[]{lobby}));
    }

    @Override
    public void removeLobbyFromList(Lobby lobby) {
        unprocessedCommands.add(new Command("removeLobbyFromList", new String[]{Lobby.class.getName()}, new Object[]{lobby}));
    }

    @Override
    public void addPlayerToLobbyInList(Lobby lobby, Player player) {
        unprocessedCommands.add(new Command(
                "addPlayersToLobbyInList",
                new String[]{Lobby.class.getName(), Player.class.getName()},
                new Object[]{lobby, player}));
    }

    @Override
    public void removePlayerFromLobbyInList(Lobby lobby, Player playerToRemove) {
        unprocessedCommands.add(new Command(
                "removePlayersFromLobbyInList",
                new String[]{Lobby.class.getName(), Player.class.getName()},
                new Object[]{lobby, playerToRemove}));
    }

    @Override
    public void addPlayer(Player player) {
        unprocessedCommands.add(new Command("addPlayer", new String[]{Player.class.getName()}, new Object[]{player}));
    }

    @Override
    public void removePlayer(Player player) {
        unprocessedCommands.add(new Command("removePlayer", new String[]{Player.class.getName()}, new Object[]{player}));
    }

    @Override
    public void startGame() {
        unprocessedCommands.add(new Command("startGame", new String[0], new Object[0]));
    }

    @Override
    public void endCurrentTurn() {
        unprocessedCommands.add(new Command("endCurrentTurn", new String[0], new Object[0]));
    }
}
