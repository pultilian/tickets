
package tickets.client.gui.presenters;

import tickets.common.UserData;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;
import tickets.client.model.observable.ClientStateChange;
import tickets.client.ModelFacade;


public class LoginPresenter implements ILoginPresenter {
	private IHolderActivity holder;
	private ClientObservable observable;

	public LoginPresenter(IHolderActivity setHolder) {
		holder = setHolder;
		ModelFacade.getInstance().linkObserver(this);
	}

//----------------------------------------------------------------------------
//	interface methods

	@Override
	public void register(UserData registerData) {
		ModelFacade.getInstance().register(registerData);
		return;
	}

	@Override
	public void login(UserData loginData) {
		try {
			ModelFacade.getInstance().login(loginData);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}

	@Override
	public void notify(IMessage state) {
		switch(state.getClass()) {
			case ClientStateChange.class:
				ClientStateChange.ClientState flag = (ClientStateChange.ClientState) state.getMessage();
				checkClientStateFlag(flag);
				break;
			case ExceptionMessage.class:
				Exception e = (Exception) state.getMessage();
				holder.toastException(e);
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
				holder.makeTransition(IHolderActivity.Transition toLobbyList);
				break;
			case ClientStateChange.ClientState.lobbylist:
				//do nothing
				break;
			case ClientStateChange.ClientState.lobby:
				//do nothing
				break;
			case ClientStateChange.ClientState.game:
				//do nothing
				break;
			default:
				Exception err = new Exception("Observer err: invalid Transition " + flag.name());
				holder.toastException(err);
				break;
		}
		return;
	}

}
