package tickets.client.model.observable;

public interface IMessage {
	
  public int getCurrentStateCode();
  
  public Object getStateData();

}