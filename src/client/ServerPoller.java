package client;

import client.model.ClientModelRoot;

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

    public void getLobbyState(){

    }

    public void getGameState(){

    }

    public void updateGame(){}

}
