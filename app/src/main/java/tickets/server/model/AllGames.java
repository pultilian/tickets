package tickets.server.model;

import tickets.common.Game;

import java.util.ArrayList;
import java.util.List;

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
}
