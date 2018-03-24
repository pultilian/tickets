
package tickets.client.gui.presenters;

import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.ITaskManager;
import tickets.client.TaskManager;
import tickets.client.async.AsyncManager;
import tickets.common.ClientModelUpdate;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.Lobby;


public class LobbyListPresenter implements ILobbyListPresenter {
    private IHolderActivity holder;
    private IObservable observable;
    private ITaskManager manager;


    public LobbyListPresenter(IHolderActivity setHolder) {
        holder = setHolder;
        manager = AsyncManager.getInstance();
        ClientFacade.getInstance().linkObserver(this);
    }
    
    public LobbyListPresenter() {
    	holder = null;
        manager = TaskManager.getInstance();
        ClientFacade.getInstance().linkObserver(this);
        ClientFacade.getInstance().startServerPoller();
    }

//----------------------------------------------------------------------------
//	interface methods

    @Override
    public List<Lobby> getLobbyList() {
        ClientFacade.getInstance().startServerPoller();
        return ClientFacade.getInstance().getLobbyList();
    }

    @Override
    public void createLobby(Lobby lobby) {
        manager.createLobby(lobby);
        return;
    }

    @Override
    public void joinLobby(String id) {
        manager.joinLobby(id);
        return;
    }

    @Override
    public void logout() {
        ClientFacade.getInstance().stopServerPoller();
        manager.logout();
        return;
    }

    @Override
    public void notify(IMessage state) {
        if (state.getClass() == ClientStateChange.class) {
            ClientStateChange.ClientState flag;
            flag = (ClientStateChange.ClientState) state.getMessage();
            checkClientStateFlag(flag);
        } else if (state.getClass() == ClientModelUpdate.class) {
        	ClientModelUpdate.ModelUpdate flag;
            flag = (ClientModelUpdate.ModelUpdate) state.getMessage();
            checkClientUpdateFlag(flag);
    	} else if (state.getClass() == ExceptionMessage.class) {
            Exception e = (Exception) state.getMessage();
            if(holder != null)
                holder.toastException(e);
            else
                System.out.println(e.getMessage());
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
        IHolderActivity.Transition transition;
        switch (flag) {
            case login:
                transition = IHolderActivity.Transition.toLogin;
                if (holder != null)
                    holder.makeTransition(transition);
                break;
            case lobbylist:
                //do nothing
                break;
            case lobby:
                transition = IHolderActivity.Transition.toLobby;
                if (holder != null)
                    holder.makeTransition(transition);
                break;
            case game:
                //do nothing
                break;
            case update:
                holder.checkUpdate();
                break;
            default:
                Exception err = new Exception("Observer err: invalid Transition " + flag.name());
                holder.toastException(err);
                break;
        }
        return;
    }
    
    private void checkClientUpdateFlag(ClientModelUpdate.ModelUpdate flag) {
    	switch(flag) {
            case lobbyListUpdated:
    		    //Todo: This should update something, right?
    			break;
    		default:
    			break;
    	}
    }
}
