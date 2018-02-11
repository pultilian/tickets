package tickets.client.asynk;

// AsyncTask<Params, Progress, Result>
//
//  - main (GUI) thread
//    task.execute(Params... params) -> returns void
//  - background thread
//    task.doInBackground(Params... params) -> returns Result
//      -> publishProgress(Progress... progress) -> returns void
//           - main thread: task.onProgressUpdate(Progress... progress) -> returns void
//      -> return Result
//  - main thread
//    task.onPostExecute(Result result) -> returns void


// creates and manages AsyncTask objects for communicating with the server over the network
public class AsyncManager {
    private ClientModelRoot root;

    public AsyncManager(ClientModelRoot setRoot) {
        root = setRoot;
    }

    public void register(UserData userData) {
        RegisterTask task = new RegisterTask(root);
        task.execute(userData);
        return;
    }

    public void login(UserData userData) {
        LoginTask task = new LoginTask(root);
        task.execute(userData);
        return;
    }

    
}