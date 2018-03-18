package tickets.client;

import java.util.Queue;

import tickets.common.Command;
import tickets.common.Lobby;
import tickets.common.UserData;
import tickets.common.response.AddToChatResponse;
import tickets.common.response.ClientUpdate;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.response.LeaveLobbyResponse;
import tickets.common.response.LoginResponse;
import tickets.common.response.LogoutResponse;
import tickets.common.response.StartGameResponse;
import tickets.server.ServerFacade;

import org.junit.Test;
import static org.junit.Assert.*;

// To run all tests, click the 'run' button on the side of this declaration.
public class TestServer {

// Tests all possible functions on the server to ensure that correct responses are returned.
// Also tests that correct commands are sent to other clients where applicable.
// Checks for everything in the implementation rubrics for each phase (that's done on the server).
// Each test assumes the previous tests are working.
// Tests can be run independently or all together.

    @Test
    public void testRegister() {
        // Test valid register
        ServerFacade server = ServerFacade.getInstance();
        UserData data = new UserData("RegisterTest", "password");
        LoginResponse response = server.register(data);
        assertNull("Valid register rejected.", response.getException());
        assertNotNull("No auth token returned (register).", response.getAuthToken());
        assertNotNull("No lobby list returned (register).", response.getLobbyList());

        // Test valid register (same password)
        data = new UserData("RegisterTestTwo", "password");
        response = server.register(data);
        assertNull("Valid register rejected (same password).", response.getException());
        assertNotNull("No auth token returned (register).", response.getAuthToken());
        assertNotNull("No lobby list returned (register).", response.getLobbyList());

        // Test invalid register (duplicate username)
        data = new UserData("RegisterTest", "ADifferentPassword");
        response = server.register(data);
        assertNotNull("Invalid register allowed (duplicate username).", response.getException());
        assertNull("Auth token returned on invalid register.", response.getAuthToken());
        assertNull("Lobby list returned on invalid register.", response.getLobbyList());
    }

    @Test
    public void testLogin() {
        ServerFacade server = ServerFacade.getInstance();
        UserData data = new UserData("LoginTest", "password");
        server.register(data);

        // Test valid login
        LoginResponse response = server.login(data);
        assertNull("Valid login rejected.", response.getException());
        assertNotNull("No auth token returned (login).", response.getAuthToken());
        assertNotNull("No lobby list returned (login).", response.getLobbyList());

        // Test invalid login (incorrect password)
        data = new UserData("LoginTest", "NotMyPassword");
        response = server.login(data);
        assertNotNull("Invalid login allowed (incorrect password).", response.getException());
        assertNull("Auth token returned on invalid login.", response.getAuthToken());
        assertNull("Lobby list returned on invalid login.", response.getLobbyList());

        // Test invalid login (username doesn't exist)
        data = new UserData("NonexistentUsername", "password");
        response = server.login(data);
        assertNotNull("Invalid login allowed (incorrect username).", response.getException());
        assertNull("Auth token returned on invalid login.", response.getAuthToken());
        assertNull("Lobby list returned on invalid login.", response.getLobbyList());
    }

    @Test
    public void testLogout() {
        ServerFacade server = ServerFacade.getInstance();
        UserData data = new UserData("LogoutTest", "password");
        LoginResponse response = server.register(data);
        String authToken = response.getAuthToken();

        // Test valid logout
        LogoutResponse logoutResponse = server.logout(authToken);
        assertNull("Valid logout rejected.", logoutResponse.getException());

        // Test invalid logout (wrong auth token)
        authToken = "Hi. I'm a fake auth token!";
        logoutResponse = server.logout(authToken);
        assertNotNull("Invalid logout accepted (wrong auth token).", logoutResponse.getException());
    }

// All following tests will involve multiple "clients"

    @Test
    public void testCreateLobby() {
        ServerFacade server = ServerFacade.getInstance();
        UserData data = new UserData("CreateLobbyTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();
        data = new UserData("CreateLobbyTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        // Test valid create lobby
        Lobby lobby = new Lobby("Create Lobby Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        assertNull("Valid create lobby rejected.", joinLobbyResponse.getException());
        assertNotNull("No lobby returned (create lobby).", joinLobbyResponse.getLobby());
        assertNotNull("No player returned (create lobby).", joinLobbyResponse.getPlayer());

        // Test updateClient on auth2
        ClientUpdate update = server.updateClient(null, auth2);
        assertNull("Update client failed.", update.getException());
        assertNotNull("Command queue not sent (create lobby).", update.getCommands());
        assertNotNull("Last command ID not sent (create lobby).", update.getLastCommandID());
        Queue<Command> commands = update.getCommands();
        assertFalse("Command queue is empty (create lobby).", commands.isEmpty());
        Command command = commands.remove();
        assertEquals("Wrong command sent (create lobby).", "addLobbyToList", command.getMethodName());

        // Test invalid create lobby (duplicate name)
        lobby = new Lobby("Create Lobby Test", 5);
        joinLobbyResponse = server.createLobby(lobby, auth2);
        assertNotNull("Invalid create lobby allowed (duplicate name).", joinLobbyResponse.getException());
        assertNull("Lobby returned on invalid create lobby.", joinLobbyResponse.getLobby());
        assertNull("Player returned on invalid create lobby.", joinLobbyResponse.getPlayer());
    }

    @Test
    public void testJoinLobby() {
        ServerFacade server = ServerFacade.getInstance();

        UserData data = new UserData("JoinLobbyTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();

        data = new UserData("JoinLobbyTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        data = new UserData("JoinLobbyTest3", "password");
        response = server.register(data);
        String auth3 = response.getAuthToken();

        Lobby lobby = new Lobby("Join Lobby Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        String lobbyID = joinLobbyResponse.getLobby().getId();
        ClientUpdate update = server.updateClient(null, auth1);
        String lastID1 = update.getLastCommandID();
        update = server.updateClient(null, auth3);
        String lastID3 = update.getLastCommandID();

        // Test valid join lobby on auth2
        joinLobbyResponse = server.joinLobby(lobbyID, auth2);
        assertNull("Valid join lobby rejected.", joinLobbyResponse.getException());
        assertNotNull("No lobby returned (join lobby).", joinLobbyResponse.getLobby());
        assertNotNull("No player returned (join lobby).", joinLobbyResponse.getPlayer());

        // Test updateClient on auth1 (in lobby)
        update = server.updateClient(lastID1, auth1);
        assertNull("Update client failed.", update.getException());
        assertNotNull("Command queue not sent (join lobby - in lobby).", update.getCommands());
        assertNotNull("Last command ID not sent (join lobby - in lobby).", update.getLastCommandID());
        Queue<Command> commands = update.getCommands();
        assertFalse("Command queue is empty (join lobby - in lobby).", commands.isEmpty());
        Command command = commands.remove();
        assertEquals("Wrong command sent (join lobby - in lobby).", "addPlayerToLobbyInList", command.getMethodName());

        // Test updateClient on auth3 (in lobby list)
        update = server.updateClient(lastID3, auth3);
        assertNull("Update client failed.", update.getException());
        assertNotNull("Command queue not sent (join lobby - in lobby list).", update.getCommands());
        assertNotNull("Last command ID not sent (join lobby - in lobby list).", update.getLastCommandID());
        commands = update.getCommands();
        assertFalse("Command queue is empty (join lobby - in lobby list).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (join lobby - in lobby list).", "addPlayerToLobbyInList", command.getMethodName());

        // Test invalid join lobby (full lobby)
        joinLobbyResponse = server.joinLobby(lobbyID, auth3);
        assertNotNull("Invalid join lobby allowed (full lobby).", joinLobbyResponse.getException());
        assertNull("Lobby returned on invalid join lobby.", joinLobbyResponse.getLobby());
        assertNull("Player returned on invalid join lobby.", joinLobbyResponse.getPlayer());
    }

    @Test
    public void testLeaveLobby() {
        ServerFacade server = ServerFacade.getInstance();

        UserData data = new UserData("LeaveLobbyTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();

        data = new UserData("LeaveLobbyTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        data = new UserData("LeaveLobbyTest3", "password");
        response = server.register(data);
        String auth3 = response.getAuthToken();

        Lobby lobby = new Lobby("Leave Lobby Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        String lobbyID = joinLobbyResponse.getLobby().getId();
        server.joinLobby(lobbyID, auth2);

        ClientUpdate update = server.updateClient(null, auth1);
        String lastID1 = update.getLastCommandID();
        update = server.updateClient(null, auth3);
        String lastID3 = update.getLastCommandID();

        // Test leave lobby
        LeaveLobbyResponse leaveLobbyResponse = server.leaveLobby(lobbyID, auth2);
        assertNull("Valid leave lobby rejected.", leaveLobbyResponse.getException());
        assertNotNull("No lobby list returned (leave lobby).", leaveLobbyResponse.getLobbyList());

        // Test updateClient on auth1 (in lobby)
        update = server.updateClient(lastID1, auth1);
        assertNull("Update client failed.", update.getException());
        assertNotNull("Command queue not sent (leave lobby - in lobby).", update.getCommands());
        assertNotNull("Last command ID not sent (leave lobby - in lobby).", update.getLastCommandID());
        Queue<Command> commands = update.getCommands();
        assertFalse("Command queue is empty (leave lobby - in lobby).", commands.isEmpty());
        Command command = commands.remove();
        assertEquals("Wrong command sent (leave lobby - in lobby).", "removePlayerFromLobbyInList", command.getMethodName());

        // Test updateClient on auth3 (in lobby list)
        update = server.updateClient(lastID3, auth3);
        assertNull("Update client failed.", update.getException());
        assertNotNull("Command queue not sent (leave lobby - in lobby list).", update.getCommands());
        assertNotNull("Last command ID not sent (leave lobby - in lobby list).", update.getLastCommandID());
        commands = update.getCommands();
        assertFalse("Command queue is empty (leave lobby - in lobby list).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (leave lobby - in lobby list).", "removePlayerFromLobbyInList", command.getMethodName());
    }

    @Test
    public void testStartGame() {
        ServerFacade server = ServerFacade.getInstance();

        UserData data = new UserData("StartGameTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();

        data = new UserData("StartGameTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        data = new UserData("StartGameTest3", "password");
        response = server.register(data);
        String auth3 = response.getAuthToken();

        Lobby lobby = new Lobby("Start Game Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        String lobbyID = joinLobbyResponse.getLobby().getId();

        // Test invalid start game (only one player)
//        StartGameResponse startGameResponse = server.startGame(lobbyID, auth1);
//        assertNotNull("Invalid start game allowed (only one player).", startGameResponse.getException());
//        assertNull("Initial hand returned on invalid start game", startGameResponse.getPlayerHand());
//        assertNull("Game returned on invalid start game.", startGameResponse.getGame());
//        assertNull("Destination card options returned on invalid start game.", startGameResponse.getDestCardOptions());

        server.joinLobby(lobbyID, auth2);
        ClientUpdate update = server.updateClient(null, auth2);
        String lastID2 = update.getLastCommandID();
        update = server.updateClient(null, auth3);
        String lastID3 = update.getLastCommandID();

        // Test valid start game
        StartGameResponse startGameResponse = server.startGame(lobbyID, auth1);
        assertNull("Valid start game rejected.", startGameResponse.getException());
        assertNotNull("No initial hand returned on valid start game.", startGameResponse.getPlayerHand());
        assertNotNull("No game returned on valid start game.", startGameResponse.getGame());
        assertNotNull("No destination card options returned on valid start game.", startGameResponse.getDestCardOptions());
        assertNotNull("No initialized face-up deck on valid start game.", startGameResponse.getGame().getFaceUpCards());

        // Test updateClient on auth2 (in lobby)
        update = server.updateClient(lastID2, auth2);
        assertNull("Update client failed.", update.getException());
        assertNotNull("Command queue not sent (start game - in lobby).", update.getCommands());
        assertNotNull("Last command ID not sent (start game - in lobby).", update.getLastCommandID());
        Queue<Command> commands = update.getCommands();
        assertFalse("Command queue is empty (start game - in lobby).", commands.isEmpty());
        Command command = commands.remove();
        assertEquals("Wrong command sent (start game - in lobby).", "startGame", command.getMethodName());

        // Test updateClient on auth3 (in lobby list)
        update = server.updateClient(lastID3, auth3);
        assertNull("Update client failed.", update.getException());
        assertNotNull("Command queue not sent (start game - in lobby list).", update.getCommands());
        assertNotNull("Last command ID not sent (start game - in lobby list).", update.getLastCommandID());
        commands = update.getCommands();
        assertFalse("Command queue is empty (start game - in lobby list).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (start game - in lobby list).", "removeLobbyFromList", command.getMethodName());
    }

    @Test
    public void testChat() {
        ServerFacade server = ServerFacade.getInstance();

        UserData data = new UserData("ChatTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();

        data = new UserData("ChatTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        Lobby lobby = new Lobby("Chat Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        String lobbyID = joinLobbyResponse.getLobby().getId();
        server.joinLobby(lobbyID, auth2);
        server.startGame(lobbyID, auth1);
        ClientUpdate update = server.updateClient(null, auth1);
        String lastID1 = update.getLastCommandID();
        update = server.updateClient(null, auth2);
        String lastID2 = update.getLastCommandID();

        // Test chat (there's no invalid chat)
        AddToChatResponse chatResponse = server.addToChat("This is a chat message!", auth1);
        assertNull("In-game chat rejected.", chatResponse.getException());

        // Test updateClient on auth1
        update = server.updateClient(lastID1, auth1);
        assertNull("Update client failed.", update.getException());
        assertNotNull("Command queue not sent (chat - sender).", update.getCommands());
        assertNotNull("Last command ID not sent (chat - sender).", update.getLastCommandID());
        Queue<Command> commands = update.getCommands();
        assertFalse("Command queue is empty (chat - sender).", commands.isEmpty());
        Command command = commands.remove();
        assertEquals("Wrong command sent (chat - sender).", "addChatMessage", command.getMethodName());
        command.decode();
        assertEquals("Wrong chat message (sender).","This is a chat message!", command.getParameters()[0]);

        // Test updateClient on auth2
        update = server.updateClient(lastID2, auth2);
        assertNull("Update client failed.", update.getException());
        assertNotNull("Command queue not sent (chat - receiver).", update.getCommands());
        assertNotNull("Last command ID not sent (chat - receiver).", update.getLastCommandID());
        commands = update.getCommands();
        assertFalse("Command queue is empty (chat - receiver).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (chat - receiver).", "addChatMessage", command.getMethodName());
        command.decode();
        assertEquals("Wrong chat message (receiver).","This is a chat message!", command.getParameters()[0]);
    }

    @Test
    public void testInitialDestinationCards() {

    }

    @Test
    public void testDrawTrainCards() {

    }

    @Test
    public void testDrawDestinationCards() {

    }

    @Test
    public void testClaimRoute() {

    }

    @Test
    public void testEndTurn() {

    }
}
