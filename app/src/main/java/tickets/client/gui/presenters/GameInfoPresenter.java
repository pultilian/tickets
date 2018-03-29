package tickets.client.gui.presenters;

import java.util.ArrayList;
import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.ITaskManager;
import tickets.client.TaskManager;
import tickets.client.async.AsyncManager;
import tickets.common.ClientModelUpdate;
import tickets.common.ExceptionMessage;
import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.PlayerInfo;

public class GameInfoPresenter implements IGameInfoPresenter{
    private IHolderGameInfoFragment holder;
    private IObservable observable;
    private ITaskManager manager;

    public GameInfoPresenter(){
        holder = null;
        ClientFacade.getInstance().linkObserver(this);
        manager = TaskManager.getInstance();
    }

    public GameInfoPresenter(IHolderGameInfoFragment setHolder) {
        holder = setHolder;
        ClientFacade.getInstance().linkObserver(this);
        manager = AsyncManager.getInstance();
    }

    public List<String> getGameHistory(){
        return ClientFacade.getInstance().getGame().getGameHistory();
    }
    public List<PlayerInfo> getPlayerInfo(){
        return new ArrayList<PlayerInfo>(ClientFacade.getInstance().getGame().getAllPlayers());
    }

    public int getCurrentTurn(){
        return ClientFacade.getInstance().getGame().getCurrentTurn();
    }

    @Override
    public void notify(IMessage state) {
        if (state.getClass() == ClientModelUpdate.class) {
            ClientModelUpdate.ModelUpdate flag = (ClientModelUpdate.ModelUpdate) state.getMessage();
            checkClientModelUpdateFlag(flag);
        } else if (state.getClass() == ExceptionMessage.class) {
            Exception e = (Exception) state.getMessage();
            if (holder != null)
                holder.toastException(e);
            else
                System.err.println(e.getMessage());
        } else {
            Exception err = new Exception("Observer err: invalid IMessage of type " + state.getClass());
            if (holder != null)
                holder.toastException(err);
            else
                System.err.println(err.getMessage());
        }
        return;
    }

    public void setObservable(IObservable setObservable) {
        observable = setObservable;
        return;
    }

    private void checkClientModelUpdateFlag(ClientModelUpdate.ModelUpdate flag) {
        switch (flag) {
            case playerTrainHandUpdated:
                break;
            case faceUpCardUpdated:
                break;
            case playerDestHandUpdated:
                break;
            case destCardOptionsUpdated:
                break;
            case gameHistoryUpdated:
                if (holder != null)
                    holder.updateGameHistory();
                break;
            case playerInfoUpdated:
                if (holder != null)
                    holder.updatePlayerInfo();
                break;
            case chatUpdated:
                break;
            case mapUpdated:
                break;
            default:
                Exception err = new Exception("Observer err: invalid Transition " + flag.name());
                if (holder != null)
                    holder.toastException(err);
                else
                    System.err.println(err.getMessage());
                break;
        }
    }
}
