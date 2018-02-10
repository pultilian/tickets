package tickets.client;

import tickets.client.model.ClientModelRoot;

/**
 * Created by Pultilian on 2/1/2018.
 */
public class ServerPoller {
    private static ServerPoller INSTANCE = null;
    private ClientModelRoot clientModelRoot;

    public static ServerPoller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerPoller();
        }
        return (INSTANCE);
    }

    public LobbyState getLobbyState(){
        return null;
    }

    public GameState getGameState(){
        return null;
    }

    public void updateGame(){}

    public class LobbyState {

    }

    public class GameState {

    }

}
