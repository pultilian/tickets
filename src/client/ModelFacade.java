package client;

import common.UserData;

public class ModelFacade {
  //Singleton structure
	public static ModelFacade SINGLETON = new ModelFacade();
	private ModelFacade() { }
	
  //variables
  ClientModelRoot modelRoot = new ClientModelRoot();

  //methods
  public boolean register(UserData userData) {
    LoginResponse result = ServerProxy.SINGLETON.register(userData);

    if (result.getException() == null) {
      return true;
    }
    else {
      return false;
    }
  }

	public boolean login(UserData userData) {
		LoginResponse result = ServerProxy.SINGLETON.login(userData);
    
    if (result.getException() == null) {
      return true;
    }
    else {
      return false;
    }
	}

  public void saveLoginData() {
    return;
  }
  
  public void updateObservable(state change) {
    return;
  }

  public boolean joinLobby(Lobby id) {
    return false;
  }

  public boolean createLobby(Lobby Data) {
    return false;
  }

  public void saveLobbyData(Lobby Data) {
    return;
  }

  public boolean logout() {
    return false;
  }

  public boolean startGame(Lobby id) {
    return false;
  }

  public boolean leaveLobby(User id) {
    return false;
  }

  public boolean addGuest(Lobby id) {
    return false;
  }

  public boolean takeTurn(Player id) {
    return false;
  }
}
