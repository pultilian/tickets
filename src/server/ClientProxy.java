/** CLIENT PROXY
 * Client Proxy
 */
public class ClientProxy {
    private static ClientProxy INSTANCE = null;
    private ServerCommunicator serverCommunicator;
    private int authToken;
    private UpdateManager updateManager;

    public static ClientProxy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientProxy();
        }
        return (INSTANCE);
    }

    public void updateLobbyList(LobbyListData data){}
    public void updateLobby(LobbyData data){}
    public void startGame(){}
    public void updateGameState(GameData data){}
}
