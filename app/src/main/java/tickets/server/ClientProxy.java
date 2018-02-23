package tickets.server;

import tickets.common.Command;
import tickets.common.IClient;
import tickets.common.Lobby;
import tickets.common.Player;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class ClientProxy implements IClient {

    private String authToken;
    private Map<Command, String> commandIDs;
    private Queue<Command> unprocessedCommands;
    private Double totalCommandsSoFar;

    public ClientProxy(String authToken){
        this.authToken = authToken;
        commandIDs = new HashMap<>();
        unprocessedCommands = new ArrayDeque<>();
        totalCommandsSoFar = (double)0;
    }

    public String getAuthToken(){ return authToken; }
    public Queue<Command> getUnprocessedCommands() { return unprocessedCommands; }
    public Map<Command, String> getCommandIDs() { return commandIDs; }

    public void setUnprocessedCommands(Queue<Command> unprocessedCommands) { this.unprocessedCommands = unprocessedCommands; }
    public void setCommandIDs(Map<Command, String> commandIDs) { this.commandIDs = commandIDs; }

    @Override
    public void addLobbyToList(Lobby lobby) {
        Command command = new Command("addLobbyToList", new String[]{Lobby.class.getName()}, new Object[]{lobby});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void removeLobbyFromList(Lobby lobby) {
        Command command = new Command("removeLobbyFromList", new String[]{Lobby.class.getName()}, new Object[]{lobby});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void addPlayerToLobbyInList(Lobby lobby, Player player) {
        Command command = new Command(
                "addPlayersToLobbyInList",
                new String[]{Lobby.class.getName(), Player.class.getName()},
                new Object[]{lobby, player});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void removePlayerFromLobbyInList(Lobby lobby, Player playerToRemove) {
        Command command = new Command(
                "removePlayersFromLobbyInList",
                new String[]{Lobby.class.getName(), Player.class.getName()},
                new Object[]{lobby, playerToRemove});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void addPlayer(Player player) {
        Command command = new Command("addPlayer", new String[]{Player.class.getName()}, new Object[]{player});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void removePlayer(Player player) {
        Command command = new Command("removePlayer", new String[]{Player.class.getName()}, new Object[]{player});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void startGame() {
        Command command = new Command("startGame", new String[0], new Object[0]);
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void endCurrentTurn() {
        Command command = new Command("endCurrentTurn", new String[0], new Object[0]);
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void addChatMessage(String message) {
        Command command = new Command("addChatMessage", new String[]{String.class.getName()}, new Object[]{message});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }
}
