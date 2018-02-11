package tickets.client.model.observable;

import java.util.List;
import java.util.Observable;

public class ClientObservable extends Observable {
	private List<IObserver> allObservers;

	public void linkObserver(IObserver observer) {
		observer.setObservable(this);
		observers.add(observer);
		return;
	}

	private void updateObservers(IStateChange change) {
		return;
	}

	private void notify(IStateChange change) {
		return;
	}
}
