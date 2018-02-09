package cs_340.client.model.observable;

public interface IStateChange {
  public int getCurrentStateCode();
  public Object getStateData();
}