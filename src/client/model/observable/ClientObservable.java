package client.model.observable;

import java.util.Observable;

public class ClientObservable extends Observable {
	private List<I_Observer> observers;

  private void updateObservers(I_StateChange change) {
    return;
  }

  private void notify(I_StateChange change) {
    return;
  }
}
