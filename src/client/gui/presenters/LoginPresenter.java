package client.gui.presenters;

import client.model.observable.IObserver;
import client.model.observable.IStateChange;
import common.UserData;
import client.ModelFacade;


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


  //function from I_Observer
  @Override
  public void updateObservable(IStateChange state) {
    return;
  }

  //function from I_Observer
  @Override
  public void notify(IStateChange state) {
    return;
  }

}
