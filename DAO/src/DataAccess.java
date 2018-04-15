import common.Command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Pultilian on 4/9/2018.
 */
public class DataAccess {
    private static String databaseName = "jdbc:sqlite:database.db";
    protected static Connection connection = null;
    protected PreparedStatement statement;

    public DataAccess() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(databaseName);
    }

    public boolean checkIfEmpty() {
        return true;
    }

    public void openConnection() throws Exception {
        connection = DriverManager.getConnection(databaseName);
    }

    public static void createTable(String tableType) throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseName);
            PreparedStatement statement = connection.prepareStatement(tableType);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() throws Exception{
        connection.close();
        connection = null;
    }

    public void addDeltas(List<Command> commands){

    }

    public List<Command> getDeltas(){
        return null;
    }
}
