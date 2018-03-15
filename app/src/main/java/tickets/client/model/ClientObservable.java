package tickets.client.model;

import java.util.ArrayList;
import java.util.List;

import tickets.common.IMessage;
import tickets.common.IObservable;
import tickets.common.IObserver;

public class ClientObservable implements IObservable {
    private List<IObserver> observers;

    public ClientObservable() {
        observers = new ArrayList<>();
    }

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
