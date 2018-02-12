package tickets.client;

import tickets.common.Command;
import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;
import tickets.common.ClientStateChange.ClientState;
import tickets.common.response.ClientUpdate;


/**
 * Created by Pultilian on 2/1/2018.
 */
public class ServerPoller implements IObserver {
	private IObservable observable;
    private ClientState clientState;
    private String lastCommand;


	//Singleton
    private static ServerPoller INSTANCE = null;
    public static ServerPoller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerPoller();
        }
        return (INSTANCE);
    }

    private ServerPoller() {
        ModelFacade.getInstance().linkObserver(this);
        clientState = null;
        lastCommand = null;
        return;
    }
    
    //Should be called asynchronously, every second
    public void checkServer() {
    	String token = ModelFacade.getInstance().getAuthToken();
    	ClientUpdate updates = ServerProxy.getInstance().updateClient(lastCommand, token);
    	for(Command c:updates.getCommands()){
    		c.execute(ModelFacade.getInstance());
    	}
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
		return;
	}

    @Override
    public void setObservable(IObservable setObservable) {
        observable = setObservable;
    }
}
