
package tickets.common;

public interface IObservable {
	public void linkObserver(IObserver link);
	public void removeObserver(IObserver remove);
	public void notify(IMessage state);
}