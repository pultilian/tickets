
package tickets.client.gui.presenters;

import java.util.List;

import tickets.client.async.AsyncManager;
import tickets.common.Lobby;
import tickets.common.IMessage;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.IObservable;

import tickets.client.ClientFacade;


public class LobbyListPresenter implements ILobbyListPresenter {
    private IHolderActivity holder;
    private IObservable observable;

    public LobbyListPresenter(IHolderActivity setHolder) {
        holder = setHolder;
        ClientFacade.getInstance().linkObserver(this);
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
        AsyncManager.getInstance().createLobby(lobby);
        return;
    }

    @Override
    public void joinLobby(String id) {
        AsyncManager.getInstance().joinLobby(id);
        return;
    }

    @Override
    public void logout() {
        ClientFacade.getInstance().stopServerPoller();
        AsyncManager.getInstance().logout();
        return;
    }

    @Override
    public void notify(IMessage state) {
        System.out.println("Being notified");
        if (state.getClass() == ClientStateChange.class) {
            ClientStateChange.ClientState flag;
            flag = (ClientStateChange.ClientState) state.getMessage();
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
        IHolderActivity.Transition transition;
        switch (flag) {
            case login:
                transition = IHolderActivity.Transition.toLogin;
                holder.makeTransition(transition);
                break;
            case lobbylist:
                //do nothing
                break;
            case lobby:
                transition = IHolderActivity.Transition.toLobby;
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
}
