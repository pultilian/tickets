
package tickets.common;


public class PlayerInfo {
	private Faction playerFaction;
	private String name;
	private int points;
	private int shipsLeft;

	public void setFaction(Faction set) {
		playerFaction = set;
		return;
	}

	public Faction getFaction() {
		return playerFaction;
	}

	public void setName(String set) {
		name = set;
		return;
	}

	public String getName() {
		return name;
	}

	public void setScore(int set) {
		points = set;
		return;
	}

	public int getScore() {
		return points;
	}

	public void setShipsLeft(int set) {
		shipsLeft = set;
		return;
	}

	public int getShipsLeft() {
		return shipsLeft;
	}
}