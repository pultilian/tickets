package tickets.client;

import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.ClientStateChange.ClientState;
/**
 * Created by Pultilian on 2/1/2018.
 */
public class ServerPoller implements IObserver {
	
	//Singleton
    private static ServerPoller INSTANCE = null;
    public static ServerPoller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerPoller();
        }
        return (INSTANCE);
    }
    
    //State management
    private ClientState clientState = null;
    
    //Should be called asynchronously, every second
    private void checkServer() {
    	switch(clientState) {
    		case login:
    			break;
    		case lobbylist:
    			checkLobbyList();
    			break;
    		case lobby:
    			checkLobbyState();
    			break;
    		case game:
    			checkGameState();
    			break;
    		default:
    			break;
    	}
    }
    
    private void checkLobbyList() {
		//Call function of server proxy or client communicator
		
	}

	public void checkLobbyState(){
		//Call function of server proxy or client communicator
    }

    public void checkGameState(){
    	//Call function of server proxy or client communicator
    }

	@Override
	public void notify(IMessage message) {
		switch(message.getClass().getName()) {
			case "ClientStateChange":
				clientState = (ClientState) message.getMessage();
				break;
			default:
				break;
		}
		
	}

}
