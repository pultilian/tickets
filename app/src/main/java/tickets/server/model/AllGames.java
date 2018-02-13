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

    private List<Game> games;

    private AllGames(){
        games = new ArrayList<>();
    }

    public void addGame(Game game){ games.add(game); }

    public Game getGame(String gameID){
        for (Game game : games){
            if (game.getGameId().equals(gameID)) return game;
        }
        return null;
    }
}
