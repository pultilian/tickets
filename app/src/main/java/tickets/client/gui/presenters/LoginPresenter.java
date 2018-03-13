
package tickets.client.gui.presenters;

import tickets.client.async.AsyncManager;
import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.UserData;

import tickets.client.ClientFacade;


public class LoginPresenter implements ILoginPresenter {
    private IHolderActivity holder;
    private IObservable observable;

    public LoginPresenter(IHolderActivity setHolder) {
        holder = setHolder;
        ClientFacade.getInstance().linkObserver(this);
    }

//----------------------------------------------------------------------------
//	interface methods

    @Override
    public void register(UserData registerData) {
        if (!registerData.checkValues()) {
            holder.toastException(new Exception("invalid username or password"));
        }
        else {
            System.out.println("presenter calling model facade");
            AsyncManager.getInstance().register(registerData);
        }
        return;
    }

    @Override
    public void login(UserData loginData) {
        if (!loginData.checkValues()) {
            holder.toastException(new Exception("invalid username or password"));
        }
        else {
            AsyncManager.getInstance().login(loginData);
        }
        return;
    }

    @Override
    public void notify(IMessage state) {
        System.out.println("Being notified");
        if (state.getClass() == ClientStateChange.class) {
            ClientStateChange.ClientState flag = (ClientStateChange.ClientState) state.getMessage();
            checkClientStateFlag(flag);
        } else if (state.getClass() == ExceptionMessage.class) {
            Exception e = (Exception) state.getMessage();
            holder.toastException(e);
        } else {
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
                //do nothing
                break;
            case lobbylist:
                holder.makeTransition(IHolderActivity.Transition.toLobbyList);
                break;
            case lobby:
                //do nothing
                break;
            case game:
                //do nothing
                break;
            case update:
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
