package tickets.client.model;

import java.util.List;

import tickets.common.IMessage;
import tickets.common.IObserver;
import tickets.common.IObservable;

public class ClientObservable implements IObservable {
	private List<IObserver> observers;

	@Override
	public void linkObserver(IObserver observer) {
		observer.setObservable(this);
		observers.add(observer);
		return;
	}
	
	@Override
	public void removeObserver(IObserver o) {
		observers.remove(o);
	}

	@Override
	public void notify(IMessage message) {
		for (IObserver o : observers) {
			o.notify(message);
		}
		return;
	}
}
