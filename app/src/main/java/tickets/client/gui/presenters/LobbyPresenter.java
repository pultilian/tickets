
package tickets.client.gui.presenters;

import tickets.common.Lobby;
import tickets.common.IMessage;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;

import tickets.client.ModelFacade;


public class LobbyPresenter implements ILobbyPresenter {
    private IObservable observable;
    private IHolderActivity holder;

    public LobbyPresenter(IHolderActivity setHolder) {
        holder = setHolder;
        ModelFacade.getInstance().linkObserver(this);
    }

//----------------------------------------------------------------------------
//  interface methods

    @Override
    public Lobby getLobby() {
        return ModelFacade.getInstance().getLobby();
    }

    @Override
    public void startGame(String lobbyID) {
        ModelFacade.getInstance().startGame(lobbyID);
        return;
    }

    @Override
    public void leaveLobby(String lobbyID) {
        ModelFacade.getInstance().leaveLobby(lobbyID);
        return;
    }

    @Override
    public void addGuest(String lobbyID) {
        ModelFacade.getInstance().addGuest(lobbyID);
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
                //do nothing
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
