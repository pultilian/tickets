package tickets.client.gui.presenters;

import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.UserData;
import tickets.client.ModelFacade;


public class LoginPresenter implements IObserver {

  public void register(UserData registerData) {
    try {
      ModelFacade.getInstance().register(registerData);
    } catch(Exception e) {
      e.printStackTrace();
    }
    return;
  }

  public void login(UserData loginData) {
    try {
      ModelFacade.getInstance().login(loginData);
    } catch(Exception e) {
      e.printStackTrace();
    }
    return;
  }

  public void notify(IMessage state) {
    return;
  }

}
