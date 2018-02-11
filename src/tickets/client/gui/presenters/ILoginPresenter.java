
package tickets.client.gui.presenters;

import tickets.common.UserData;

import tickets.client.model.observable.*;
import tickets.client.ModelFacade;


public interface ILoginPresenter extends IObserver {

	public void register(UserData registerData);
	public void login(UserData loginData);

	// from IObserver
	public void notify(IMessage state);
    public void setObservable(ClientObservable setObservable);
}
