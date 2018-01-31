package client.gui.presenters;

import client.model.observable.I_Observer;
import client.model.ModelFacade;


public class LoginPresenter implements I_Observer {

  public void register() {
    UserData registerData;
    ModelFacade.SINGLETON.register(registerData);
    return;
  }

  public void login() {
    UserData loginData;
    ModelFacade.SINGLETON.login(loginData);
    return;
  }


  //function from I_Observer
  @Override
  public void updateObservable(I_StateChange state) {
    return;
  }

  //function from I_Observer
  @Override
  public void notify(I_StateChange state) {
    return;
  }

}
