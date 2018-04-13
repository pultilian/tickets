package tickets.server.dataAccess;

import tickets.common.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pultilian on 4/10/2018.
 */
public class Main {



    public static void main(String [] args){
        try {
            DAOFacade daoFacade = new DAOFacade("relational");
            Game game = new Game("1", "Game 1");
            Game game1 = new Game("2", "Game 2");
            List<Game> gameList = new ArrayList<>();
            gameList.add(game);
            gameList.add(game1);
            daoFacade.addGames(gameList);
        } catch (Exception e){

        }

    }

}
