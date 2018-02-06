package cs_340.client.model.observable;

public interface IObserver {

  /** Alert the observable about a change in global state, with the
    * understanding that the observable will notify all the other observers
    * of the state change. Each observer is given autonomy to react to the
    * change in state independently.
  **/
  public void updateObservable(IStateChange state);


  /** Meant to be called by the Observable - this function defines
    * what the observer will do in reaction to a change in global state.
  **/
  public void notify(IStateChange state);

}