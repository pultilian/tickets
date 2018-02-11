
package tickets.common.response.data;

import tickets.common.response.Response;

//Contains all data the client needs to display the current lobby.
//Clients will request this once on entering the Lobby Screen - 
//	all changes to the Lobby will be communicated via the specified ClientUpdate subclass
public class LobbyData extends Response {

	//
	public static class LobbyUpdate extends ClientUpdate {
		//
	}
} 