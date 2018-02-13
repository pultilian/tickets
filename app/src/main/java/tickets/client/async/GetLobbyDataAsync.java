
// package tickets.client.async;

// import tickets.common.response.data.LobbyData;

// import tickets.client.model.ClientModelRoot;
// import tickets.client.ServerProxy;
// import tickets.client.model.observable.*;


// public class GetLobbyDataAsync /*extends AsyncTask<String, Void, LobbyData>*/ {
// 	ClientModelRoot modelRoot;

// 	public GetLobbyDataAsync(ClientModelRoot root) {
// 		modelRoot = root;
// 	}

// 	public void execute(Object... args) {}

// 	// @Override
// 	public LobbyData doInBackgrount(String... args) {
// 		if (args.length != 2) {
// 			AsyncException error = new AsyncException(this.getClass());
// 			return new LobbyData(error);
// 		}

// 		String lobbyID = args[0];
// 		String authToken = args[1];
// 		LobbyData response = ServerProxy.getInstance().getLobbyData(lobbyID, authToken);
// 		return response;
// 	}

// 	// @Override
// 	public void onPostExecute(LobbyData response) {
// 		if (response.getException() == null) {
// 			modelRoot.setCurrentLobby(response.getData());
// 		}
// 		else {
// 			Exception ex = response.getException();
// 			ExceptionMessage msg = new ExceptionMessage(ex);
// 			modelRoot.updateObservable(msg);
// 		}

// 		return;
// 	}
// }