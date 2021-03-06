package tickets.common;

public interface IObserver {
  /** Called by the Observable to notify observers of some change
  **/
  public void notify(IMessage message);
  public void setObservable(IObservable observable);
}