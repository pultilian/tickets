package tickets.testing;

import java.util.ArrayList;
import java.util.List;

import tickets.client.ClientFacade;
import tickets.common.Faction;
import tickets.common.Player;
import tickets.common.PlayerInfo;
import tickets.common.PlayerSummary;

public class StubFacade {
    private static List<PlayerSummary> gameSummary = new ArrayList<>();

    public StubFacade() {
        PlayerInfo playerInfo1 = new PlayerInfo();
        PlayerInfo playerInfo2 = new PlayerInfo();
        playerInfo1.setFaction(Faction.Player1);
        playerInfo2.setFaction(Faction.Player2);
        playerInfo1.setName("Spencer");
        playerInfo2.setName("Dallin");
        playerInfo1.setShipsLeft(5);
        playerInfo2.setShipsLeft(1);
        playerInfo1.setScore(65);
        playerInfo2.setScore(78);
        PlayerSummary playerSummary1 = new PlayerSummary(playerInfo1, 35, 12, true);
        PlayerSummary playerSummary2 = new PlayerSummary(playerInfo2, 25, 7, false);
        gameSummary.add(playerSummary1);
        gameSummary.add(playerSummary2);
    }

    public List<PlayerSummary> getGameSummary() {
        return gameSummary;
    }
}
