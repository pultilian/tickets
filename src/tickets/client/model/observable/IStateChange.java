package tickets.client.model.observable;

public interface IStateChange {
	
  public int getCurrentStateCode();
  
  public Object getStateData();

}