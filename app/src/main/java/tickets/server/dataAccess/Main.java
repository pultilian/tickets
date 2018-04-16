package tickets.server.dataAccess;


import java.util.ArrayList;
import java.util.List;

import tickets.common.Command;
import tickets.common.Game;
import tickets.common.Lobby;

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

            Lobby lobby1 = new Lobby("lobby1", 3);
            lobby1.setId("1");
            Lobby lobby2 = new Lobby("lobby2", 4);
            lobby2.setId("2");
            Lobby lobby3 = new Lobby("lobby3", 2);
            lobby3.setId("3");

            List<Lobby> lobbyList = new ArrayList<>();
            List<Game> gameList = new ArrayList<>();
            gameList.add(game1);
            gameList.add(game2);
            gameList.add(game3);
            lobbyList.add(lobby1);
            lobbyList.add(lobby2);
            lobbyList.add(lobby3);

            Object object = new Object();
            Object[] objects = {object};
            String[] strings = {"New", "No"};

            Command command1 = new Command("Hello1",strings, objects);
            Command command2 = new Command("Hello2",strings, objects);
            Command command3 = new Command("Hello3",strings, objects);

            daoFacade.addLobbies(lobbyList);
            daoFacade.removeLobby("2");
            lobbyList = daoFacade.getLobbies();

            List<String> gameid = new ArrayList<>();
            gameid.add("1");
            daoFacade.addDelta(command1,"lobby",gameid);
            daoFacade.addDelta(command2,"lobby",gameid);
            daoFacade.addDelta(command3,"lobby",gameid);

            List<Command> newObjects = daoFacade.getDeltas("lobby", gameid);

            gameList = daoFacade.getGames();
            gameList = null;


        } catch (Exception e) {
            System.out.print(e);
        }

    }

}
