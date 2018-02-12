package tickets.common.response;

import tickets.common.Lobby;

import java.util.List;

public class JoinLobbyResponse extends Response {

  private Lobby lobby;


	public JoinLobbyResponse(Exception exception) {
		super(exception);
	}

  public JoinLobbyResponse(Lobby lobby){
    this.lobby = lobby;
  }

  public Lobby getLobby() { 
  	return lobby;
  }
}
