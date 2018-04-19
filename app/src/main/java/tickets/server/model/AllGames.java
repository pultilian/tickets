package tickets.server.model;

import java.util.ArrayList;
import java.util.List;

import tickets.common.Game;
import tickets.server.model.game.ServerGame;
import tickets.server.model.game.ServerPlayer;

public class AllGames {

    private static AllGames INSTANCE = null;

    public static AllGames getInstance(){
        if (INSTANCE == null){
            INSTANCE = new AllGames();
        }
        return INSTANCE;
    }

    private List<ServerGame> games;

    private AllGames(){
        games = new ArrayList<>();
    }

    public void addGame(ServerGame game){ games.add(game); }

    public ServerGame getGame(String gameID){
        for (ServerGame game : games){
            if (game.getGameId().equals(gameID)) return game;
        }
        return null;
    }

    public List<Game> getGamesWithUser(String username) {
        List<Game> result = new ArrayList<>();
        for (ServerGame serverGame : games) {
            for (ServerPlayer player : serverGame.getServerPlayers()) {
                if (player.getName().equals(username)) {
                    result.add(serverGame.getClientGame());
                    break;
                }
            }
        }
        return result;
    }

    public List<ServerGame> getServerGamesWithUser(String username) {
        List<ServerGame> result = new ArrayList<>();
        for (ServerGame serverGame : games) {
            for (ServerPlayer player : serverGame.getServerPlayers()) {
                if (player.getName().equals(username)) {
                    result.add(serverGame);
                    break;
                }
            }
        }
        return result;
    }

    public List<ServerGame> getAllGames() {
        return games;
    }
}
