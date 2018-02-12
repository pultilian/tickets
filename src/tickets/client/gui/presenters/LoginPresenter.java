
package tickets.client.gui.presenters;

import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.UserData;

import tickets.client.ModelFacade;
import tickets.client.gui.presenters.IHolderActivity;


public class LoginPresenter implements ILoginPresenter {
	private IHolderActivity holder;
	private IObservable observable;

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
		if (state.getClass() == ClientStateChange.class) {
			ClientStateChange.ClientState flag = (ClientStateChange.ClientState) state.getMessage();
			checkClientStateFlag(flag);
		}
		else if (state.getClass() == ExceptionMessage.class) {
			Exception e = (Exception) state.getMessage();
			holder.toastException(e);
		}
		else {
			Exception err = new Exception("Observer err: invalid IMessage of type " + state.getClass());
			holder.toastException(err);
		}

		return;
	}

	@Override
	public void setObservable(IObservable setObservable) {
		observable = setObservable;
		return;
	}

//----------------------------------------------------------------------------
//	private methods

	private void checkClientStateFlag(ClientStateChange.ClientState flag) {
		switch (flag) {
			case login:
				holder.makeTransition(IHolderActivity.Transition.toLobbyList);
				break;
			case lobbylist:
				//do nothing
				break;
			case lobby:
				//do nothing
				break;
			case game:
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
