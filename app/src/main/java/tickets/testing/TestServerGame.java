
package tickets.testing;

import java.util.ArrayList;

import tickets.common.Player;

import tickets.server.model.game.ServerGame;
import tickets.server.model.game.ServerPlayer;

public class TestServerGame {
    public static void main(String[] args) {
        System.out.println("Starting TestServerGame.main");
        TestServerGame test = new TestServerGame();

        System.out.println("\t" + test.runAll());
    }


    public String runAll() {
        try {
            testBasicInit();

        } catch (Exception ex) {
            return ex.toString();
        }

        return "All tests passed";
    }

    private void testBasicInit() throws Exception {
        Player player1 = new Player("player1_authToken");
        Player player2 = new Player("player2_authToken");
        ArrayList<Player> listPlayers = new ArrayList<>();
        listPlayers.add(player1);
        listPlayers.add(player2);

        ServerGame game = new ServerGame("gameID 1", "Game 1", listPlayers);

        ServerPlayer svrPlayer1 = game.getServerPlayer(player1.getAssociatedAuthToken());
        ServerPlayer svrPlayer2 = game.getServerPlayer(player2.getAssociatedAuthToken());

        if (svrPlayer1 == null || svrPlayer2 == null) {
            throw new Exception("ERROR with ServerGame.getServerPlayer(Strung authToken)");
        }

        return;
    }
}