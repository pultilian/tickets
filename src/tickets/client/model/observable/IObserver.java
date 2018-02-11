package tickets.client.model.observable;

public interface IObserver {
  /** Called by the Observable to notify observers of some change
  **/
  public void notify(IMessage message);
  public void setObservable(ClientObservable observable);
}