package tickets.client.model.observable;

public class ClientStateChange implements IMessage {
	
	public enum ClientState { login, lobbylist, lobby, game };
	private ClientState newState;
	
	public ClientStateChange(ClientState newState) {
		this.newState = newState;
	}
	
	@Override
	public Object getMessage() {
		return newState;
	}

}
