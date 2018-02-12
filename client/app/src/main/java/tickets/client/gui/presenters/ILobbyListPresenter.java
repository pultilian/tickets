
package tickets.client.gui.presenters;

import java.util.Map;

import tickets.common.Lobby;

import tickets.client.model.observable.*;


public interface ILobbyListPresenter extends IObserver {

	public Map<String, Lobby> getLobbyList();

	public void createLobby(Lobby lobby);
	public void joinLobby(String id);
	public void logout();

	// from IObserver
	public void notify(IMessage state);
	public void setObservable(ClientObservable setObservable);
}
