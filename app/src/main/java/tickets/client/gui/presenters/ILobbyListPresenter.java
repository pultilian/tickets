
package tickets.client.gui.presenters;

import java.util.List;

import tickets.common.Game;
import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.IObserver;
import tickets.common.Lobby;


public interface ILobbyListPresenter extends IObserver {

	public List<Lobby> getLobbyList();
	List<Lobby> getCurrentLobbies();
	List<Game> getCurrentGames();

	public void createLobby(Lobby lobby);
	public void joinLobby(String id);
	void resumeLobby(String lobbyID);
	void resumeGame(String gameID);
	public void logout();

	// from IObserver
	public void notify(IMessage state);
	public void setObservable(IObservable setObservable);
}
