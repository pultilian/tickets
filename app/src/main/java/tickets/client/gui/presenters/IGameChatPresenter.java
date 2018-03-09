
package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.IObserver;

public interface IGameChatPresenter {
	public void addToChat(String message);
	public List<String> getChatHistory();
}