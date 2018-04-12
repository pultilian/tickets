import common.*;

import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class DAOFacade {
    private DAOFactory daoFactory;
    private LobbyDataAccess lobbiesDA;
    private GameDataAccess gamesDA;
    private PlayerDataAccess playersDA;
    private UserDataAccess usersDA;
    
    public void addGames(List<Game> games) throws Exception{
        for(int i = 0; i < games.size(); i++) {
            gamesDA.addGame(games.get(i));
        }
    }
    
    public List<Game> getGames() throws Exception{
        return gamesDA.getGames();
    }
    
    public void removeGame(String gameID) throws Exception{
        gamesDA.removeGames(gameID);
    }

    public void addLobbies(List<Lobby> lobbies) throws Exception{
        for(int i = 0; i < lobbies.size(); i++) {
            lobbiesDA.addLobby(lobbies.get(i));
        }
    }

    public List<Lobby> getLobbies() throws Exception{
        return lobbiesDA.getLobbies();
    }

    public void removeLobby(String lobbyID) throws Exception{
        lobbiesDA.removeLobbies(lobbyID);
    }

    public void addUsers(List<UserData> users) throws Exception{
        for(int i = 0; i < users.size(); i++) {
            usersDA.addUserData(users.get(i));
        }
    }

    public List<UserData> getUsers() throws Exception{
        return usersDA.getUserDatas();
    }

    public void removeUser(String userID) throws Exception{
        usersDA.removeUserDatas(userID);
    }

    public void addPlayers(List<Player> players) throws Exception{
        for(int i = 0; i < players.size(); i++) {
            playersDA.addPlayer(players.get(i));
        }
    }

    public List<Player> getPlayers() throws Exception{
        return playersDA.getPlayers();
    }

    public void removePlayer(String playerID) throws Exception{
        playersDA.removePlayers(playerID);
    }
}
