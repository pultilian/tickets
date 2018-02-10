package tickets.client.model;

import java.util.List;

import tickets.common.IMessage;
import tickets.common.IObserver;

public class ClientObservable {
	private List<IObserver> observers;

	public void addObserver(IObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(IObserver o) {
		observers.remove(o);
	}

	public void notify(IMessage message) {
		for (IObserver o:observers) {
			o.notify(message);
		}
		return;
	}
}
