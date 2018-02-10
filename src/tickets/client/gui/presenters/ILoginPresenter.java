
package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IMessage;
import tickets.common.UserData;
import tickets.client.ModelFacade;


public interface ILoginPresenter implements IObserver {

  public void register(UserData registerData);
  public void registerResponse(boolean success);

  public void login(UserData loginData);
  public void loginResponse(boolean success);

  // from IObserver
  public void notify(IMessage state);

}
