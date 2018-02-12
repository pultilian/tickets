package tickets.client;

import tickets.common.Command;
import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.ClientStateChange.ClientState;
import tickets.common.response.ClientUpdate;
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
    private String lastCommand = null;
    
    //Should be called asynchronously, every second
    public void checkServer() {
    	String token = ModelFacade.getInstance().authenticate();
    	ClientUpdate updates = ServerProxy.getInstance().updateClient(lastCommand, token);
    	for(Command c:updates.getCommands()){
    		c.execute(ModelFacade.getInstance());
    	}
    }
    

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
