
package tickets.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lobby {

	private String name;
	private String id;
	private List<String> history;
	private int currentMembers;
	private int maxMembers;
	private List<Player> players;
	private List<Faction> availableFactions;
	
	public Lobby(String name, int maxMembers) {
		this.name = name;
		this.id = null;
		currentMembers = 0;
		this.maxMembers = maxMembers;
		history = new ArrayList<>();
		players = new ArrayList<>();
		availableFactions = new ArrayList<>(Arrays.asList(Faction.values()));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getHistory() {
		return history;
	}

	public void addToHistory(String message) { history.add(message); }

	public void setHistory(List<String> setHistory) { history = setHistory;	}

	public int getCurrentMembers() { return currentMembers; }

	public void setCurrentMembers(int currentMembers) { this.currentMembers = currentMembers; }

	public int getMaxMembers() {
		return maxMembers;
	}

	public void setMaxMembers(int maxMembers) {
		this.maxMembers = maxMembers;
	}

	public List<Player> getPlayers() { return players; }

	// For when a user leaves a lobby
	public List<Player> getPlayersWithAuthToken(String authToken){
		List<Player> result = new ArrayList<>();
		for (Player player : players) {
			if (player.getAssociatedAuthToken().equals(authToken)) result.add(player);
		}
		return result;
	}

	public void addPlayer(Player player) {
		players.add(player);
		assignFaction(player);
		currentMembers++;
	}

	public void removePlayer(Player player) {
		players.remove(player);
		unassignFaction(player);
		currentMembers--;
	}

	public void assignFaction(Player player) {
	    if (player.getPlayerFaction() == null) {
            int choice = new Random().nextInt(availableFactions.size());
            player.setPlayerFaction(availableFactions.get(choice));
            availableFactions.remove(choice);
        }
	}

	public void unassignFaction(Player player) {
	    Faction faction = player.getPlayerFaction();
	    player.setPlayerFaction(null);
	    availableFactions.add(faction);
    }
}
