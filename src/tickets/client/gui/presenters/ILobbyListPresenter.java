
package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;

public interface ILobbyListPresenter extends IObserver {

	public void createLobby(Lobby lobby);
	public void joinLobby(String id);
	public void logout();

	// from IObserver
	public void notify(IMessage state);
	public void setObservable(ClientObservable setObservable);
}
