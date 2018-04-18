package tickets.server.dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tickets.common.Command;
import tickets.common.Game;
import tickets.common.Lobby;
import tickets.common.Player;
import tickets.common.UserData;
import tickets.server.dataaccess.interfaces.DAOFactory;
// import tickets.file_dao.FileFactory;
// import tickets.relational_dao.RelationalFactory;
import tickets.server.dataaccess.interfaces.GameDataAccess;
import tickets.server.dataaccess.interfaces.LobbyDataAccess;
import tickets.server.dataaccess.interfaces.PlayerDataAccess;
import tickets.server.dataaccess.interfaces.UserDataAccess;

import java.util.ArrayList;
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


    public DAOFacade(String type) throws Exception {
        PluginManager manager = new PluginManager();
        daoFactory = manager.getPlugin(type);

        daoFactory.createDAOs();
        lobbiesDA = daoFactory.getLobbyDA();
        usersDA = daoFactory.getUserDA();
        gamesDA = daoFactory.getGameDA();
        playersDA = daoFactory.getPlayerDA();
    }


    public String objectToJSON(Game request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Game JSONToGame(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Game.class);
    }

    public String objectToJSON(Command request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Command JSONToCommand(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Command.class);
    }

    public String objectToJSON(Player request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Player JSONToPlayer(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Player.class);
    }

    public String objectToJSON(Lobby request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public Lobby JSONToLobby(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, Lobby.class);
    }

    public String objectToJSON(UserData request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(request);
    }

    public UserData JSONToUserData(String body) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(body, UserData.class);
    }


    public void addGames(List<Game> games) throws Exception{
        for(int i = 0; i < games.size(); i++) {
            gamesDA.addGame(objectToJSON(games.get(i)), games.get(i).getGameId());
        }
    }

    public List<Game> getGames() throws Exception{
        List<String> listStrings = gamesDA.getGames();
        List<Game> listGames = new ArrayList<>();
        for(int i = 0; i < listStrings.size(); i++){
            listGames.add(JSONToGame(listStrings.get(i)));
        }
        return listGames;
    }

    public void removeGame(String gameID) throws Exception{
        gamesDA.removeGames(gameID);
    }

    public void addLobbies(List<Lobby> lobbies) throws Exception{
        for(int i = 0; i < lobbies.size(); i++) {
            lobbiesDA.addLobby(objectToJSON(lobbies.get(i)), lobbies.get(i).getId());
        }
    }

    public List<Lobby> getLobbies() throws Exception{
        List<String> listStrings = lobbiesDA.getLobbies();
        List<Lobby> listLobby = new ArrayList<>();
        for(int i = 0; i < listStrings.size(); i++){
            listLobby.add(JSONToLobby(listStrings.get(i)));
        }
        return listLobby;
    }

    public void removeLobby(String lobbyID) throws Exception{
        lobbiesDA.removeLobbies(lobbyID);
    }

    public void addUsers(List<UserData> users) throws Exception{
        for(int i = 0; i < users.size(); i++) {
            usersDA.addUserData(objectToJSON(users.get(i)), users.get(i).getAuthenticationToken());
        }
    }

    public List<UserData> getUsers() throws Exception{
        List<String> listStrings = usersDA.getUserData();
        List<UserData> listUsers = new ArrayList<>();
        for(int i = 0; i < listStrings.size(); i++){
            listUsers.add(JSONToUserData(listStrings.get(i)));
        }
        return listUsers;
    }

    public void removeUser(String userID) throws Exception{
        usersDA.removeUserData(userID);
    }

    public void addPlayers(List<Player> players) throws Exception{
        for(int i = 0; i < players.size(); i++) {
            playersDA.addPlayer(objectToJSON(players.get(i)),
                    players.get(i).getAssociatedAuthToken(),
                    players.get(i).getName());
        }
    }

    public List<Player> getPlayers() throws Exception{
        List<String> listStrings = playersDA.getPlayers();
        List<Player> listPlayers = new ArrayList<>();
        for(int i = 0; i < listStrings.size(); i++){
            listPlayers.add(JSONToPlayer(listStrings.get(i)));
        }
        return listPlayers;
    }

    public void removePlayer(String username, String game_ID) throws Exception{
        playersDA.removePlayers(username, game_ID);
    }

    /** AddDeltas
     *
     * @param command
     * @param type (player,lobby,game)
     * @param additionalInfo **
     *     ** player - gameID, username
     *     ** lobby - lobbyID
     *     ** game -
     */
    public void addDelta(Command command, String type, List<String> additionalInfo) throws Exception{

        String commandJSON = objectToJSON(command);

        switch (type) {
            case "player":
                playersDA.addDeltas(commandJSON, additionalInfo.get(0), additionalInfo.get(1));
                break;
            case "lobby":
                lobbiesDA.addDeltas(commandJSON, additionalInfo.get(0));
                break;
            case "game":
                gamesDA.addDeltas(commandJSON, additionalInfo.get(0));
                break;
            default:
                throw new Exception(type + " is not a valid delta");
        }

    }

    /** GET DELTAS
     *
     * @param type (player,lobby,game)
     * @return a List of Commands
     */
    public List<Command> getDeltas(String type, List<String> IDs) throws Exception{
        List<String> deltas = new ArrayList<>();
        switch (type) {
            case "player":
                deltas = playersDA.getDeltas(IDs.get(0), IDs.get(1));
                break;
            case "lobby":
                deltas = lobbiesDA.getDeltas(IDs.get(0));
                break;
            case "game":
                deltas = gamesDA.getDeltas(IDs.get(0));
                break;
            default:
                throw new Exception(type + " is not a valid delta");
        }

        List<Command> retCommands = new ArrayList<>();
        for (int i = 0; i < deltas.size(); i++){
            retCommands.add(JSONToCommand(deltas.get(i)));
        }

        return retCommands;
    }
}
