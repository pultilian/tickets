package tickets.client;

import java.util.Timer;
import java.util.TimerTask;

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
    private String lastCommand;
    private Timer timer;
    private ClientState clientState;

    public ServerPoller() {
        ModelFacade.getInstance().linkObserver(this);
        clientState = null;
        lastCommand = null;
        timer = new Timer();
        timer.schedule(CheckServer, 0, 1000);
    }
    
    private TimerTask CheckServer = new TimerTask() {
	    String token = ModelFacade.getInstance().getAuthToken();
	    
    	@Override
    	public void run(){
    	    ClientUpdate updates = ServerProxy.getInstance().updateClient(lastCommand, token);
    	    for(Command c:updates.getCommands()){
    	    	c.execute(ModelFacade.getInstance());
    	    }
    	}
    };    
    
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
