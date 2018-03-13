
package tickets.client.gui.presenters;

import tickets.client.async.AsyncManager;
import tickets.common.Lobby;
import tickets.common.IMessage;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.IObservable;

import tickets.client.ClientFacade;


public class LobbyPresenter implements ILobbyPresenter {
    private IObservable observable;
    private IHolderActivity holder;

    public LobbyPresenter(IHolderActivity setHolder) {
        holder = setHolder;
        ClientFacade.getInstance().linkObserver(this);
    }

//----------------------------------------------------------------------------
//  interface methods

    @Override
    public Lobby getLobby() {
        return ClientFacade.getInstance().getLobby();
    }

    @Override
    public void startGame(String lobbyID) {
        AsyncManager.getInstance().startGame(lobbyID);
        return;
    }

    @Override
    public void leaveLobby(String lobbyID) {
        AsyncManager.getInstance().leaveLobby(lobbyID);
        return;
    }

    @Override
    public void notify(IMessage state) {
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
//  private methods

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
                holder.makeTransition(IHolderActivity.Transition.toGame);
                break;
            case update:
                holder.checkUpdate();
                break;
            default:
                Exception err = new Exception("Observer err: invalid Transition " + flag.name());
                holder.toastException(err);
                break;
        }
    }
}
