package tickets.server.dataaccess;

import tickets.common.Game;

import java.util.ArrayList;
import java.util.List;
import tickets.common.Command;
import tickets.common.Game;
import tickets.common.Lobby;
import tickets.common.Player;
import tickets.common.UserData;
import tickets.server.model.game.ServerGame;
import tickets.server.model.game.ServerPlayer;

/**
 * Created by Pultilian on 4/10/2018.
 */
public class Main {


    public static void main(String[] args) {
        try {
            DAOFacade daoFacade = new DAOFacade("File");
            ServerGame game1 = new ServerGame(new Game("1","game1"));
            ServerGame game2 = new ServerGame(new Game("2", "game2"));
            ServerGame game3 = new ServerGame(new Game("3", "game3"));

            Lobby lobby1 = new Lobby("lobby1", 3);
            lobby1.setId("1");
            Lobby lobby2 = new Lobby("lobby2", 4);
            lobby2.setId("2");
            Lobby lobby3 = new Lobby("lobby3", 2);
            lobby3.setId("3");

            ServerPlayer p1 = new ServerPlayer(new Player("1"));
            p1.setName("name1");
            ServerPlayer p2 = new ServerPlayer(new Player("2"));
            p2.setName("name2");
            ServerPlayer p3 = new ServerPlayer(new Player("3"));
            p3.setName("name3");

            UserData u1 = new UserData("halla", "yolo");
            u1.setAuthenticationToken("1");
            UserData u2 = new UserData("jk", "jk");
            u1.setAuthenticationToken("2");
            UserData u3 = new UserData("spyro", "toomba");
            u1.setAuthenticationToken("3");

            List<Lobby> lobbyList = new ArrayList<>();
            List<ServerGame> gameList = new ArrayList<>();
            List<ServerPlayer> playerList = new ArrayList<>();
            List<UserData> userDataList = new ArrayList<>();
            gameList.add(game1);
            gameList.add(game2);
            gameList.add(game3);
            lobbyList.add(lobby1);
            lobbyList.add(lobby2);
            lobbyList.add(lobby3);
            playerList.add(p1);
            playerList.add(p2);
            playerList.add(p3);
            userDataList.add(u1);
            userDataList.add(u2);
            userDataList.add(u3);

            Object object = new Object();
            Object[] objects = {object};
            String[] strings = {"New", "No"};

            Command command1 = new Command("Hello1",strings, objects);
            Command command2 = new Command("Hello2",strings, objects);
            Command command3 = new Command("Hello3",strings, objects);
            daoFacade.addGames(gameList);
            daoFacade.addUsers(userDataList);
            daoFacade.addPlayers(playerList);
            daoFacade.removeUser("3");
            daoFacade.removePlayer("name1", "1");
            gameList = daoFacade.getGames();
            userDataList = daoFacade.getUsers();

            List<String> gameid = new ArrayList<>();
            gameid.add("2");
            gameid.add("name2");
            daoFacade.addDelta(command1,"player",gameid);
            daoFacade.addDelta(command2,"player",gameid);

            List<Command> newObjects = daoFacade.getDeltas("player", gameid);

            gameList = daoFacade.getGames();
            gameList = null;
        } catch (Exception e) {
            System.out.print(e);
        }

    }

}
