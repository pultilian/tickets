package tickets.client.async;

class AsyncException extends Exception {
    public AsyncException() {
        super("An error occured in an AsyncTask");
    }

    public AsyncException(Class task) {
        super("An error occurred in " + task.toString());
    }

    public AsyncException(Class task, String msg) {
        super("An error occurred in " + task.toString() + ": " + msg);
    }
}