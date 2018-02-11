package tickets.client.async;

class AsyncException extends Exception {
    public AsyncException() {
        super("An error occured in an AsyncTask");
    }

    public AsyncException(AsyncTask task) {
        super("An error occurred in " + task.getClass().toString());
    }

    public AsyncException(AsyncTask task, String msg) {
        super("An error occurred in " + task.getClass().toString() + ": " + msg);
    }
}