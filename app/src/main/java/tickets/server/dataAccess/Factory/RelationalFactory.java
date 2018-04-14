package tickets.server.dataAccess.Factory;

import tickets.server.dataAccess.DataAccess;
import tickets.server.dataAccess.Interfaces.DAOFactory;
import tickets.server.dataAccess.Interfaces.GameDataAccess;
import tickets.server.dataAccess.Interfaces.LobbyDataAccess;
import tickets.server.dataAccess.Interfaces.PlayerDataAccess;
import tickets.server.dataAccess.Interfaces.UserDataAccess;
import tickets.server.dataAccess.RelationalDAO.GameDA_Rel;
import tickets.server.dataAccess.RelationalDAO.LobbyDA_Rel;
import tickets.server.dataAccess.RelationalDAO.PlayerDA_Rel;
import tickets.server.dataAccess.RelationalDAO.UserDA_Rel;

/**
 * Created by Pultilian on 4/12/2018.
 */

public class RelationalFactory implements DAOFactory {
    private DataAccess dataAccess;
    private GameDataAccess gameDA;
    private LobbyDataAccess lobbyDA;
    private PlayerDataAccess playerDA;
    private UserDataAccess userDA;


    public void createDatabase() throws Exception{
        String statement = "CREATE TABLE users (id TEXT PRIMARY KEY, userData BLOB)";
        dataAccess.createTable(statement);
        statement = "CREATE TABLE lobbies (id TEXT PRIMARY KEY, lobby BLOB)";
        dataAccess.createTable(statement);
        statement = "CREATE TABLE lobbydeltas (id TEXT PRIMARY KEY, command BLOB)";
        dataAccess.createTable(statement);
        statement = "CREATE TABLE players (username TEXT, game_id TEXT, player BLOB)";
        dataAccess.createTable(statement);
        statement = "CREATE TABLE playerdeltas (username TEXT, game_id TEXT, command BLOB)";
        dataAccess.createTable(statement);
        statement = "CREATE TABLE games (id TEXT, game BLOB)";
        dataAccess.createTable(statement);
        statement = "CREATE TABLE gamedeltas (id TEXT, command BLOB)";
        dataAccess.createTable(statement);

    }

    @Override
    public void createDAOs() throws Exception{
        dataAccess = new DataAccess();
        createDatabase();
        gameDA = new GameDA_Rel();
        lobbyDA = new LobbyDA_Rel();
        playerDA = new PlayerDA_Rel();
        userDA = new UserDA_Rel();
    }

    @Override
    public LobbyDataAccess getLobbyDA(){
        return this.lobbyDA;
    }

    @Override
    public GameDataAccess getGameDA(){
        return this.gameDA;
    }

    @Override
    public PlayerDataAccess getPlayerDA(){
        return this.playerDA;
    }

    @Override
    public UserDataAccess getUserDA(){
        return this.userDA;
    }
}
