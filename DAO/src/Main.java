import common.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pultilian on 4/10/2018.
 */
public class Main {



    public static void main(String [] args){
        DAOFacade daoFacade = new DAOFacade();
        Game game = new Game("1");
        Game game1 = new Game("2");
        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game1);

        try {
            daoFacade.addGames(gameList);
        } catch (Exception e){

        }

    }

}
