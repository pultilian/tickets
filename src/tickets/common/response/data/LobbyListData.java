
package tickets.common.response.data;

import java.util.Map;

import tickets.common.response.Response;
import tickets.common.Lobby;


//Contains all data the client needs to display the current list of lobbies.
//Clients will request this once on entering the LobbyList Screen - 
//	all changes to the LobbyList will be communicated via the specified ClientUpdate subclass
public class LobbyListData extends Response {

	public LobbyListData(Exception e) {
		super(e);
	}

	public Map<String, Lobby> getData() {
		return null;
	}

	//
	public static class LobbyListUpdate extends ClientUpdate {
		//
	}
} 