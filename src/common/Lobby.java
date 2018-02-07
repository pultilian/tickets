package common;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

	private String name;
	private String id;
	private List<String> history;
	private int currentMembers;
	private int maxMembers;
	
	public Lobby(String name, int maxMembers) {
		this.name = name;
		this.id = null;
		this.maxMembers = maxMembers;
		List<String> history = new ArrayList<String>();
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

	public int getCurrentMembers() {
		return currentMembers;
	}

	public int getMaxMembers() {
		return maxMembers;
	}

	public void setMaxMembers(int maxMembers) {
		this.maxMembers = maxMembers;
	}
}
