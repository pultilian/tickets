
package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;
import tickets.common.UserData;
import tickets.client.ModelFacade;


public interface ILoginPresenter implements IObserver {

	public void setHolder(IHolderActivity holder);

	public void register(UserData registerData);
	public void login(UserData loginData);

	// from IObserver
	public void notify(IMessage state);

}
