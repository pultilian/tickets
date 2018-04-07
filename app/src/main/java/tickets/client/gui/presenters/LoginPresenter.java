
package tickets.client.gui.presenters;

import tickets.client.ClientFacade;
import tickets.client.ITaskManager;
import tickets.client.TaskManager;
import tickets.client.async.AsyncManager;
import tickets.common.ClientModelUpdate;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.UserData;


public class LoginPresenter implements ILoginPresenter {
    private IHolderActivity holder;
    private IObservable observable;
    private ITaskManager manager;

    public LoginPresenter(IHolderActivity setHolder) {
        holder = setHolder;
        manager = AsyncManager.getInstance();
        ClientFacade.getInstance().linkObserver(this);
    }
    
    public LoginPresenter() {
    	holder = null;
        manager = TaskManager.getInstance();
        ClientFacade.getInstance().linkObserver(this);
    }

//----------------------------------------------------------------------------
//	interface methods

    @Override
    public boolean register(UserData registerData) {
        if (!registerData.checkValues()) {
        	if (holder != null)
        		holder.toastException(new Exception("Invalid username or password"));
        	else {
        		System.out.println("Invalid username or password");
        		return false;
        	}
        }
        else {
            manager.register(registerData);
        }
        return true;
    }

    @Override
    public boolean login(UserData loginData) {
        if (!loginData.checkValues()) {
        	if (holder != null)
        		holder.toastException(new Exception("Invalid username or password"));
        	else {
        		System.out.println("Invalid username or password");
        		return false;
        	}
        }
        else {
            manager.login(loginData);
        }
        return true;
    }

    @Override
    public void notify(IMessage state) {
        if (state.getClass() == ClientStateChange.class) {
            ClientStateChange.ClientState flag = (ClientStateChange.ClientState) state.getMessage();
            checkClientStateFlag(flag);
        } else if (state.getClass() == ClientModelUpdate.class){
        	//Do nothing
        }else if (state.getClass() == ExceptionMessage.class) {
            Exception e = (Exception) state.getMessage();
            if (holder != null)
                holder.toastException(e);
            else
                System.out.println(e.getMessage());
        } else {
            Exception err = new Exception("Observer err: unrecognized IMessage of type " + state.getClass());
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
                if (holder != null)
                    holder.makeTransition(IHolderActivity.Transition.toLobbyList);
                break;
            case lobby:
                //do nothing
                break;
            case game:
                //do nothing
                break;
            case summary:
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
