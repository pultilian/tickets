package tickets.server;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import tickets.common.ChoiceDestinationCards;
import tickets.common.Command;
import tickets.common.Game;
import tickets.common.GameSummary;
import tickets.common.HandTrainCard;
import tickets.common.IClient;
import tickets.common.Lobby;
import tickets.common.Player;
import tickets.common.PlayerColor;
import tickets.common.PlayerSummary;
import tickets.common.Route;
import tickets.common.RouteColors;
import tickets.common.TrainCard;

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

    public String getAuthToken(){
        return authToken;
    }

    public Queue<Command> getUnprocessedCommands() {
        return unprocessedCommands;
    }

    public Map<Command, String> getCommandIDs() {
        return commandIDs;
    }

    public void setUnprocessedCommands(Queue<Command> unprocessedCommands) {
        this.unprocessedCommands = unprocessedCommands;
    }
    public void setCommandIDs(Map<Command, String> commandIDs) {
        this.commandIDs = commandIDs;
    }

    @Override
    public void addLobbyToList(Lobby lobby) {
        Command command = new Command("addLobbyToList",
                new String[]{Lobby.class.getName()},
                new Object[]{lobby});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void removeLobbyFromList(Lobby lobby) {
        Command command = new Command("removeLobbyFromList",
                new String[]{Lobby.class.getName()},
                new Object[]{lobby});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void addPlayerToLobbyInList(Lobby lobby, Player player) {
        Command command = new Command(
                "addPlayerToLobbyInList",
                new String[]{Lobby.class.getName(), Player.class.getName()},
                new Object[]{lobby, player});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void removePlayerFromLobbyInList(Lobby lobby, Player playerToRemove) {
        Command command = new Command(
                "removePlayerFromLobbyInList",
                new String[]{Lobby.class.getName(), Player.class.getName()},
                new Object[]{lobby, playerToRemove});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void removePlayer(Player player) {
        Command command = new Command("removePlayer",
                new String[]{Player.class.getName()},
                new Object[]{player});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void startGame(Game game, HandTrainCard playerHand, ChoiceDestinationCards destCardOptions) {
        Command command = new Command("startGame",
                new String[]{Game.class.getName(), HandTrainCard.class.getName(), ChoiceDestinationCards.class.getName()},
                new Object[]{game, playerHand, destCardOptions});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void endCurrentTurn() {
        Command command = new Command("endCurrentTurn",
                new String[0],
                new Object[0]);
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void addChatMessage(String message) {
        Command command = new Command("addChatMessage",
                new String[]{String.class.getName()},
                new Object[]{message});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void addToGameHistory(String message) {
        Command command = new Command("addToGameHistory",
                new String[]{String.class.getName()},
                new Object[]{message});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void addPlayerTrainCard() {
        Command command = new Command("addPlayerTrainCard",
                new String[0],
                new Object[0]);
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void addClaimedRoute(Route route, RouteColors routeColor, PlayerColor player) {
        Command command = new Command("addClaimedRoute",
                new String[]{Route.class.getName(), RouteColors.class.getName(), PlayerColor.class.getName()},
                new Object[]{route, routeColor, player});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void addPlayerDestinationCards(String playerName, Integer num) {
        Command command = new Command("addPlayerDestinationCards",
                new String[]{String.class.getName(), Integer.class.getName()},
                new Object[]{playerName, num});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void displayEndGame(GameSummary gameSummary) {
        Command command = new Command("displayEndGame",
                new String[] {GameSummary.class.getName()},
                new Object[] {gameSummary});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    @Override
    public void replaceFaceUpCard(Integer position, TrainCard card) {
        Command command = new Command("replaceFaceUpCard",
                new String[] {Integer.class.getName(), TrainCard.class.getName()},
                new Object[] {position, card});
        unprocessedCommands.add(command);
        commandIDs.put(command, totalCommandsSoFar.toString());
        totalCommandsSoFar++;
    }

    public void clearCommands() {
        unprocessedCommands.clear();
        commandIDs.clear();
    }
}
