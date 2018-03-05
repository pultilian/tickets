
package tickets.client.gui.presenters;

import java.util.List;

public interface IGameChatPresenter {
	public void addToChat(String message);
	public List<String> getChatHistory();
}