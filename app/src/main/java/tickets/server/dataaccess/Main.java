package tickets.server.dataaccess;

import tickets.common.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pultilian on 4/10/2018.
 */
public class Main {


    public static void main(String[] args) {
        try {
            DAOFacade daoFacade = new DAOFacade("relational");
            Game game1 = new Game("1","game1");
            Game game2 = new Game("2", "game2");
            Game game3 = new Game("3", "game3");
            List<Game> gameList = new ArrayList<>();
            gameList.add(game1);
            gameList.add(game2);
            gameList.add(game3);
            daoFacade.addGames(gameList);

        } catch (Exception e) {

        }

    }

}
