package tickets.common;

public class ExceptionMessage implements IMessage {
  private Exception message;

  public ExceptionMessage(Exception e) {
    message = e;
  }

  public Object getMessage() {
    return message;
  }
}