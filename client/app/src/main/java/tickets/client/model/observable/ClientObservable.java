package tickets.client.model.observable;

import java.util.List;
import java.util.ArrayList;

public class ClientObservable {
    private List<IObserver> observers;

	public ClientObservable() {
        observers = new ArrayList<>();
    }

    public void linkObserver(IObserver observer) {
        observer.setObservable(this);
        observers.add(observer);
        return;
    }

    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    public void notify(IMessage message) {
        for (IObserver o : observers) {
            o.notify(message);
        }
        return;
    }
}
