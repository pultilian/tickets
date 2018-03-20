
package tickets.client.gui.presenters;

import java.util.List;

import tickets.client.ModelFacade;
import tickets.common.ClientModelUpdate;
import tickets.common.ClientStateChange;
import tickets.common.ExceptionMessage;
import tickets.common.IMessage;
import tickets.common.IObservable;

public class GameChatPresenter implements IGameChatPresenter {
	private IHolderGameChatFragment holder;
	private IObservable observable;

	public GameChatPresenter(IHolderGameChatFragment setHolder) {
		holder = setHolder;
		ModelFacade.getInstance().linkObserver(this);
	}

	public void addToChat(String message){
		ModelFacade.getInstance().addToChat(message);
	}

	public List<String> getChatHistory(){
		return ModelFacade.getInstance().getGame().getChat();
	}

	@Override
	public void notify(IMessage state) {
		if (state.getClass() == ClientModelUpdate.class) {
			ClientModelUpdate.ModelUpdate flag = (ClientModelUpdate.ModelUpdate) state.getMessage();
			checkClientModelUpdateFlag(flag);
		} else if (state.getClass() == ExceptionMessage.class) {
			Exception e = (Exception) state.getMessage();
			holder.toastException(e);
		} else {
			Exception err = new Exception("Observer err: invalid IMessage of type " + state.getClass());
			holder.toastException(err);
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
				break;
			case playerInfoUpdated:
				break;
			case chatUpdated:
				holder.updateChat();
				break;
			case mapUpdated:
				break;
			default:
				Exception err = new Exception("Observer err: invalid Transition " + flag.name());
				holder.toastException(err);
				break;
		}
	}
}