package tickets.client;

import java.util.Timer;
import java.util.TimerTask;

import tickets.common.Command;
import tickets.common.response.ClientUpdate;


/**
 * ServerPoller class is responsible for sending regular (once per minute)
 *  requests to the server for commands to be executed on the client model.
*/
public class ServerPoller {
	/** Last command received from the server is sent with each request for 
	 *   updates from the server to ensure that commands which are lost can
	 *   be sent again
	*/
    private String lastCommand;
	
	/** Timer object allows for regular polling of server
	*/
    private Timer timer;
	
	/** Boolean value to check if server is running
	*/
    private boolean running;

	/** Public constructor initializes member variables
	*/
    public ServerPoller() {
        lastCommand = null;
        timer = new Timer();
        running = false;
    }
	
	/** Allows user to test if server poller is running
	 * @return Whether or not server polling is running
	*/
	public boolean isRunning() {
		return running;
	}

	/** Begins polling the server once per second to request updates
	 * @pre Poller is not already running
	 * @pre Client is connected to server
	 * @post ServerPoller begins requesting updates from the server once per
	 *        second and executes any commands returned from the server
	 * @return Whether or not starting the poller was successful
	*/
    public boolean startPolling(){
        if(! running) {
            timer.schedule(CheckServer, 0, 1000);
            running = true;
            return true;
        }
        return false;
    }

	/** Stops the poller
	 * @pre Poller is running
	 * @post Poller is no longer running
	*/
    public void stopPolling(){
		if(running)
			timer.cancel();
    }
    
	/** TimerTask for server polling; sends a request to server for updates,
	 *   executes any commands received from the server
	*/
    private TimerTask CheckServer = new TimerTask() {
		/** Stores token required by the server for receiving requests
		*/
	    String token = ClientFacade.getInstance().getAuthToken();
	    
		/** This is the regularly executed task for checking for updates from
		 *   the server and handling those updates
		 * @pre Client is connected to server
		 * @post Commands from server are executed on the client model
		*/
    	@Override
    	public void run() {
    		token = ClientFacade.getInstance().getAuthToken();
    	    ClientUpdate updates = ServerProxy.getInstance().updateClient(lastCommand, token);
			// If there are no exceptions from the server
    	    if(updates.getException() == null) {
				// If there are no updates, end
    	        if (updates.getCommands() == null)
                    return;
				// Execute each command
                for (Command c : updates.getCommands()) {
					// Ensure that parameters have been decoded from json strings
                    c.decode();
                    c.execute(ClientFacade.getInstance());
                }
				// Save last command for the next request
                lastCommand = updates.getLastCommandID();
            }
            else {
    	        System.err.println(updates.getException().getMessage());
            }
    	}
    };
}
