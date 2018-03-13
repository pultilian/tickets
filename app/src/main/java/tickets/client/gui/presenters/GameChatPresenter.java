
package tickets.client.gui.presenters;

import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.async.AsyncManager;

public class GameChatPresenter implements IGameChatPresenter {
	public void addToChat(String message){
		AsyncManager.getInstance().addToChat(message);
	}

	public List<String> getChatHistory(){
		return ClientFacade.getInstance().getGame().getChat();
	}
}