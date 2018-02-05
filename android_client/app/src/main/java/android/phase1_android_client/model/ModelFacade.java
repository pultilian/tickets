package android.phase1_android_client.model;

import android.phase1_android_client.model.observable.I_StateChange;
import android.phase1_android_client.proxy.ServerProxy;

import common.LobbyId;
import common.UserData;
import common.response.*;

public class ModelFacade {
    //Singleton structure
    public static ModelFacade SINGLETON = new ModelFacade();

    private ModelFacade() {
    }

    //variables
    ClientModelRoot modelRoot = new ClientModelRoot();

    //methods
    public boolean register(UserData userData) {
        LoginResponse result = ServerProxy.SINGLETON.register(userData.getUsername(), userData.getPassword());

        if (result.getException() == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean login(UserData userData) {
        LoginResponse result = ServerProxy.SINGLETON.login(userData.getUsername(), userData.getPassword());

        if (result.getException() == null) {
            return true;
        } else {
            return false;
        }
    }

    public void saveLoginData() {
        return;
    }

    public void updateObservable(I_StateChange state) {
        return;
    }

    public boolean joinLobby(LobbyId id) {
        return false;
    }

    public boolean createLobby(LobbyData data) {
        return false;
    }

    public void saveLobbyData(LobbyData data) {
        return;
    }

    public boolean logout() {
        return false;
    }

    public boolean startGame(LobbyData data) {
        return false;
    }

    public boolean leaveLobby(UserId id) {
        return false;
    }

    public boolean addGuest(LobbyId id) {
        return false;
    }

    public boolean takeTurn(PlayerId id) {
        return false;
    }
}
