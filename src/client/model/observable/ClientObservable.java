package client.model.observable;

import java.util.List;

public class ClientObservable {
	private List<IObserver> observers;

	public void addObserver(IObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(IObserver o) {
		observers.remove(o);
	}

	public void notify(IStateChange change) {
		for (IObserver o:observers) {
			o.notify(change);
		}
		return;
	}
}
