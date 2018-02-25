package tickets.client;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tickets.common.ClientStateChange;
import tickets.common.Command;
import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;
import tickets.common.ClientStateChange.ClientState;
import tickets.common.Lobby;
import tickets.common.response.ClientUpdate;


/**
 * Created by Pultilian on 2/1/2018.
 */
public class ServerPoller implements IObserver {
	private IObservable observable;
    private String lastCommand;
    private Timer timer;
    private ClientState clientState;
    private boolean running;

    public ServerPoller() {
        ModelFacade.getInstance().linkObserver(this);
        clientState = null;
        lastCommand = null;
        timer = new Timer();
        running = false;
    }

    public boolean startPolling(){
        if(! running) {
            timer.schedule(CheckServer, 0, 1000);
            running = true;
            return true;
        }
        return false;
    }

    public void stopPolling(){
        timer.cancel();
    }
    
    private TimerTask CheckServer = new TimerTask() {
	    String token = ModelFacade.getInstance().getAuthToken();
	    
    	@Override
    	public void run() {
    	    ClientUpdate updates = ServerProxy.getInstance().updateClient(lastCommand, token);
    	    if(updates.getException() == null) {
    	        if (updates.getCommands() == null)
                    return;
                for (Command c : updates.getCommands()) {
                    System.out.println(c.getMethodName());
                    c.execute(ModelFacade.getInstance());
                }
                lastCommand = updates.getLastCommandID();
            }
            else {
    	        System.err.println(updates.getException().getMessage());
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
