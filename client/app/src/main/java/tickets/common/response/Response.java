package tickets.common.response;

public class Response {

    private Exception exception;

    public Response(Exception exception){
        this.exception = exception;
    }

    public Response(){}

    public Exception getException(){ return exception; }
}
