
package tickets.client.gui.presenters;

import java.util.List;

import tickets.client.ModelFacade;

public class GameChatPresenter implements IGameChatPresenter {
	public void addToChat(String message){
		ModelFacade.getInstance().addToChat(message);
	}
	public List<String> getChatHistory(){
		return ModelFacade.getInstance().getGame().getChat();
	}
}