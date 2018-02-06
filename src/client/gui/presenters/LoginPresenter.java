package client.gui.presenters;

import client.model.observable.IObserver;
import common.UserData;
import client.ModelFacade;


public class LoginPresenter implements IObserver {

  public void register() {
    UserData registerData;
    ModelFacade.getInstance().register(registerData);
    return;
  }

  public void login() {
    UserData loginData;
    ModelFacade.getInstance().login(loginData);
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
