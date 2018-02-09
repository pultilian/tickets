package cs_340.client.gui.presenters;

import cs_340.client.model.observable.IObserver;
import cs_340.client.model.observable.IStateChange;
import cs_340.common.UserData;
import cs_340.client.ModelFacade;


public interface ILoginPresenter extends IObserver {

  void register(UserData data);
  void login(UserData data);

  //functions from I_Observer
  void updateObservable(IStateChange state);
  void notify(IStateChange state);

}
