/** I CLIENT PROXY
 * Client Proxy Interface
 */
package tickets.common;

public interface IClientProxy {
    static IClientProxy INSTANCE = null;
    public IClientProxy getInstance();

//    public void updateLobbyList(LobbyListData data);
//    public void updateLobby(LobbyData data);
//    public void startGame();
//    public void updateGameState(GameData data);
}
