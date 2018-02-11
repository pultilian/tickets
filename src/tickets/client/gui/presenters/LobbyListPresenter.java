package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;

public class LobbyListPresenter implements ILobbyListPresenter {
	private IHolderActivity holder;
	private ClientObservable observable;

	public LobbyListPresenter(IHolderActivity setHolder) {
		holder = setHolder;
		ModelFacade.getInstance().linkObserver(this);
	}

//----------------------------------------------------------------------------
//	interface methods

	@Override
	public void createLobby(Lobby lobby) {
		ModelFacade.getInstance().createLobby(lobby);
		return;
	}

	@Override
	public void joinLobby(String id) {
		ModelFacade.getInstance().joinLobby(id);
		return;
	}

	@Override
	public void logout() {
		ModelFacade.getInstance().logout();
		return;
	}

	@Override
	public void notify(IMessage state) {
		switch (state.getClass()) {
			case ClientStateChange.class:
				//
				break;
			case ExceptionMessage.class:
				//
				break;
			default:
				Exception err = new Exception("Observer err: invalid IMessage of type " + state.getClass());
				holder.toastException(err);
				break;
		}
		return;
	}

	@Override
	public void setObservable(ClientObservable setObservable) {
		observable = setObservable;
		return;
	}

//----------------------------------------------------------------------------
//	private methods

	private void checkClientStateFlag(ClientStateChange.ClientState flag) {
		switch (flag) {
			case ClientStateChange.ClientState.login:
				//do nothing
				break;
			case ClientStateChange.ClientState.lobbylist:
				//do nothing
				break;
			case ClientStateChange.ClientState.lobby:
				holder.makeTransition(IHolderActivity.Transition toLobby);
				break;
			case ClientStateChange.ClientState.game:
				//do nothing
				break;
			default:
				Exception err = new Exception("Observer err: invalid Transition " + flag.name());
				holder.toastException(err);
				break;
		}
	}

}
