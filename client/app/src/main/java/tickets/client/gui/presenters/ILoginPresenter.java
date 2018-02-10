package tickets.client.gui.presenters;

import tickets.client.model.observable.IObserver;
import tickets.client.model.observable.IStateChange;
import tickets.common.UserData;


public interface ILoginPresenter extends IObserver {

  void register(UserData data);
  void login(UserData data);

  //functions from I_Observer
  void updateObservable(IStateChange state);
  void notify(IStateChange state);

}
