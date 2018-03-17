
package tickets.client.gui.presenters;

import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.IObserver;
import tickets.common.UserData;


public interface ILoginPresenter extends IObserver {

	public boolean register(UserData registerData);
	public boolean login(UserData loginData);

	// from IObserver
	public void notify(IMessage state);
    public void setObservable(IObservable setObservable);
}
