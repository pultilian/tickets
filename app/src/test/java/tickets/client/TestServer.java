package tickets.client;

import java.util.List;
import java.util.Queue;

import tickets.common.ChoiceDestinationCards;
import tickets.common.Command;
import tickets.common.DestinationCard;
import tickets.common.Lobby;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.UserData;
import tickets.common.response.AddToChatResponse;
import tickets.common.response.ClientUpdate;
import tickets.common.response.DestinationCardResponse;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.response.LeaveLobbyResponse;
import tickets.common.response.LoginResponse;
import tickets.common.response.LogoutResponse;
import tickets.common.response.Response;
import tickets.common.response.StartGameResponse;
import tickets.common.response.TrainCardResponse;
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
        // *** SET UP ***
        ServerFacade server = ServerFacade.getInstance();
        UserData data = new UserData("LoginTest", "password");
        server.register(data);
        // *** END SET UP ***

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
        // *** SET UP ***
        ServerFacade server = ServerFacade.getInstance();
        UserData data = new UserData("LogoutTest", "password");
        LoginResponse response = server.register(data);
        String authToken = response.getAuthToken();
        // *** END SET UP ***

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
        // *** SET UP ***
        ServerFacade server = ServerFacade.getInstance();
        UserData data = new UserData("CreateLobbyTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();
        data = new UserData("CreateLobbyTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();
        // *** END SET UP ***

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
        // *** SET UP ***
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
        // *** END SET UP ***

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
        // *** SET UP ***
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
        // *** END SET UP ***

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
        // *** SET UP ***
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
        // *** END SET UP ***

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
        // *** SET UP ***
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
        // *** END SET UP ***

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
        // *** SET UP ***
        ServerFacade server = ServerFacade.getInstance();

        UserData data = new UserData("InitialDestinationTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();

        data = new UserData("InitialDestinationTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        Lobby lobby = new Lobby("Initial Destination Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        String lobbyID = joinLobbyResponse.getLobby().getId();
        server.joinLobby(lobbyID, auth2);
        ClientUpdate update1 = server.updateClient(null, auth1);
        String lastID1 = update1.getLastCommandID();
        ClientUpdate update2 = server.updateClient(null, auth2);
        String lastID2 = update2.getLastCommandID();
        StartGameResponse startGameResponse = server.startGame(lobbyID, auth1);
        // *** END SET UP ***

        // Test destination cards received by each player.
        List<DestinationCard> cards1 = startGameResponse.getDestCardOptions().getDestinationCards();
        assertNotNull("No destination cards received on start game (starting player)", cards1);
        update2 = server.updateClient(lastID2, auth2);
        assertNull("Update client failed.", update2.getException());
        assertNotNull("Command queue not sent (start game - not starting player).", update2.getCommands());
        assertNotNull("Last command ID not sent (start game - not starting player).", update2.getLastCommandID());
        Queue<Command> commands = update2.getCommands();
        assertFalse("Command queue is empty (start game - not starting player).", commands.isEmpty());
        Command command = commands.remove();
        assertEquals("Wrong command sent (start game - not starting player).", "startGame", command.getMethodName());
        command.decode();
        ChoiceDestinationCards cards2Choice = (ChoiceDestinationCards)command.getParameters()[2];
        List<DestinationCard> cards2 = cards2Choice.getDestinationCards();
        assertNotNull("No destination cards received on start game (not starting player).", cards2);

        // Test received three cards each and all are different
        assertEquals("Wrong number of destination cards received on start game (starting player).",cards1.size(), 3);
        assertEquals("Wrong number of destination cards received on start game (not starting player).",cards2.size(), 3);
        assertNotEquals("Duplicate destination cards on start game.", cards1.get(0), cards1.get(1));
        assertNotEquals("Duplicate destination cards on start game.", cards1.get(1), cards1.get(2));
        assertNotEquals("Duplicate destination cards on start game.", cards1.get(2), cards1.get(0));

        assertNotEquals("Duplicate destination cards between players on start game.", cards1.get(0), cards2.get(0));
        assertNotEquals("Duplicate destination cards between players on start game.", cards1.get(0), cards2.get(1));
        assertNotEquals("Duplicate destination cards between players on start game.", cards1.get(0), cards2.get(2));
        assertNotEquals("Duplicate destination cards between players on start game.", cards1.get(1), cards2.get(0));
        assertNotEquals("Duplicate destination cards between players on start game.", cards1.get(1), cards2.get(1));
        assertNotEquals("Duplicate destination cards between players on start game.", cards1.get(1), cards2.get(2));
        assertNotEquals("Duplicate destination cards between players on start game.", cards1.get(2), cards2.get(0));
        assertNotEquals("Duplicate destination cards between players on start game.", cards1.get(2), cards2.get(1));
        assertNotEquals("Duplicate destination cards between players on start game.", cards1.get(2), cards2.get(2));

        assertNotEquals("Duplicate destination cards on start game.", cards2.get(0), cards2.get(1));
        assertNotEquals("Duplicate destination cards on start game.", cards2.get(1), cards2.get(2));
        assertNotEquals("Duplicate destination cards on start game.", cards2.get(2), cards2.get(0));

        // Test discarding one card (player 1) and update history
        Response discardResponse = server.discardDestinationCard(cards1.get(0), auth1);
        assertNull("Valid initial discard destination card rejected.", discardResponse.getException());

        update1 = server.updateClient(lastID1, auth1);
        assertNull("Update client failed.", update1.getException());
        assertNotNull("Command queue not sent (update history).", update1.getCommands());
        assertNotNull("Last command ID not sent (update history).", update1.getLastCommandID());
        commands = update1.getCommands();
        assertFalse("Command queue is empty (update history).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (update history).", "addToGameHistory", command.getMethodName());

        update2 = server.updateClient(lastID2, auth2);
        assertNull("Update client failed.", update2.getException());
        assertNotNull("Command queue not sent (update history).", update2.getCommands());
        assertNotNull("Last command ID not sent (update history).", update2.getLastCommandID());
        commands = update2.getCommands();
        assertFalse("Command queue is empty (update history).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (update history).", "addToGameHistory", command.getMethodName());
        command = commands.remove();
        assertEquals("Wrong command sent (start game = after update history).",
                "removePlayerDestinationCard", command.getMethodName());

        // Test can't do anything else until all players discard
        TrainCardResponse trainCardResponse = server.drawTrainCard(auth1);
        assertNotNull("Action allowed before all players finished initializing cards.",trainCardResponse.getException());
        assertEquals("Wrong exception message (during attempt to play before all players initialize)",
                trainCardResponse.getException().getMessage(), "It is not your turn.");

        // Test able to not discard any cards
        discardResponse = server.discardDestinationCard(null, auth2);
        assertNull("Valid initial discard destination card rejected (no discard).", discardResponse.getException());
    }

    @Test
    public void testDrawTrainCards() {
        // *** SET UP ***
        ServerFacade server = ServerFacade.getInstance();

        UserData data = new UserData("DrawTrainCardTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();

        data = new UserData("DrawTrainCardTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        Lobby lobby = new Lobby("Draw Train Card Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        String lobbyID = joinLobbyResponse.getLobby().getId();
        server.joinLobby(lobbyID, auth2);
        ClientUpdate update1 = server.updateClient(null, auth1);
        String lastID1 = update1.getLastCommandID();
        ClientUpdate update2 = server.updateClient(null, auth2);
        String lastID2 = update2.getLastCommandID();
        StartGameResponse startGameResponse = server.startGame(lobbyID, auth1);

        List<DestinationCard> cards1 = startGameResponse.getDestCardOptions().getDestinationCards();
        update2 = server.updateClient(lastID2, auth2);
        Queue<Command> commands = update2.getCommands();
        Command command = commands.remove();
        command.decode();
        ChoiceDestinationCards cards2Choice = (ChoiceDestinationCards)command.getParameters()[2];
        List<DestinationCard> cards2 = cards2Choice.getDestinationCards();
        server.discardDestinationCard(cards1.get(0), auth1);
        server.discardDestinationCard(cards2.get(0), auth2);

        update1 = server.updateClient(lastID1, auth1);
        lastID1 = update1.getLastCommandID();
        update2 = server.updateClient(lastID2, auth2);
        lastID2 = update2.getLastCommandID();
        // *** END SET UP ***

        // Test valid draw card from deck
        TrainCardResponse trainCardResponse = server.drawTrainCard(auth1);
        assertNull("Valid draw train card rejected.", trainCardResponse.getException());
        assertNotNull("No train card returned on valid draw train card.", trainCardResponse.getCard());

        // Test update history for both players
        update1 = server.updateClient(lastID1, auth1);
        assertNull("Update client failed.", update1.getException());
        assertNotNull("Command queue not sent (update history).", update1.getCommands());
        assertNotNull("Last command ID not sent (update history).", update1.getLastCommandID());
        commands = update1.getCommands();
        assertFalse("Command queue is empty (update history).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (update history).", "addToGameHistory", command.getMethodName());

        update2 = server.updateClient(lastID2, auth2);
        assertNull("Update client failed.", update2.getException());
        assertNotNull("Command queue not sent (update history).", update2.getCommands());
        assertNotNull("Last command ID not sent (update history).", update2.getLastCommandID());
        commands = update2.getCommands();
        assertFalse("Command queue is empty (update history).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (update history).", "addToGameHistory", command.getMethodName());
        command = commands.remove();
        assertEquals("Wrong command sent (draw train card - after update history).",
                "addPlayerTrainCard", command.getMethodName());

        List<TrainCard> cards = startGameResponse.getGame().getFaceUpCards();
        int position = 0;
        for (TrainCard card : cards) {
            if (card.getColor() == RouteColors.Wild) {

                // NOTE: I cannot guarantee that a wild face-up card will be found due to
                // randomness of face-up cards. This code may not be run.

                // Test cannot draw face-up wild card on second draw
                trainCardResponse = server.drawFaceUpCard(position, auth1);
                assertNotNull("Invalid draw train card allowed (face-up wild on 2nd draw).", trainCardResponse.getException());
                assertNull("Train card returned on invalid draw train card (face-up wild on 2nd draw).", trainCardResponse.getCard());
            }
            position++;
        }

        // Test valid draw non-wild face-up card
        position = 0;
        while (cards.get(position).getColor() == RouteColors.Wild) position++;
        trainCardResponse = server.drawFaceUpCard(position, auth1);
        assertNull("Valid draw train card rejected (2nd draw from face-up).", trainCardResponse.getException());
        assertNotNull("No train card returned on valid draw train card (2nd draw from face-up).", trainCardResponse.getCard());

        // Test 3rd draw not allowed
        trainCardResponse = server.drawTrainCard(auth1);
        assertNotNull("Invalid draw train card allowed (3rd draw card).", trainCardResponse.getException());
        assertNull("Train card returned on invalid draw train card (3rd draw card).", trainCardResponse.getCard());
    }

    @Test
    public void testEndTurn() {
        // *** SET UP ***
        ServerFacade server = ServerFacade.getInstance();

        UserData data = new UserData("EndTurnTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();

        data = new UserData("EndTurnTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        Lobby lobby = new Lobby("End Turn Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        String lobbyID = joinLobbyResponse.getLobby().getId();
        server.joinLobby(lobbyID, auth2);
        ClientUpdate update1 = server.updateClient(null, auth1);
        String lastID1 = update1.getLastCommandID();
        ClientUpdate update2 = server.updateClient(null, auth2);
        String lastID2 = update2.getLastCommandID();
        StartGameResponse startGameResponse = server.startGame(lobbyID, auth1);

        List<DestinationCard> cards1 = startGameResponse.getDestCardOptions().getDestinationCards();
        update2 = server.updateClient(lastID2, auth2);
        Queue<Command> commands = update2.getCommands();
        Command command = commands.remove();
        command.decode();
        ChoiceDestinationCards cards2Choice = (ChoiceDestinationCards)command.getParameters()[2];
        List<DestinationCard> cards2 = cards2Choice.getDestinationCards();
        server.discardDestinationCard(cards1.get(0), auth1);
        server.discardDestinationCard(cards2.get(0), auth2);

        server.drawTrainCard(auth1);
        server.drawTrainCard(auth1);

        update1 = server.updateClient(lastID1, auth1);
        lastID1 = update1.getLastCommandID();
        update2 = server.updateClient(lastID2, auth2);
        lastID2 = update2.getLastCommandID();
        // *** END SET UP ***

        // Test player 2 cannot perform actions until player 1 ends turn
        TrainCardResponse trainCardResponse = server.drawTrainCard(auth2);
        assertNotNull("Invalid action allowed (not my turn).", trainCardResponse.getException());
        assertNull("Train card given on invalid action (not my turn).", trainCardResponse.getCard());

        // Test player 2 end turn does nothing
        Response serverResponse = server.endTurn(auth2);
        assertNotNull("End turn allowed when not my turn.", serverResponse.getException());

        // Test player 1 ends turn
        serverResponse = server.endTurn(auth1);
        assertNull("Valid end turn rejected.", serverResponse.getException());

        // Test update history for both players
        update1 = server.updateClient(lastID1, auth1);
        assertNull("Update client failed.", update1.getException());
        assertNotNull("Command queue not sent (update history).", update1.getCommands());
        assertNotNull("Last command ID not sent (update history).", update1.getLastCommandID());
        commands = update1.getCommands();
        assertFalse("Command queue is empty (update history).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (update history).", "addToGameHistory", command.getMethodName());

        update2 = server.updateClient(lastID2, auth2);
        assertNull("Update client failed.", update2.getException());
        assertNotNull("Command queue not sent (update history).", update2.getCommands());
        assertNotNull("Last command ID not sent (update history).", update2.getLastCommandID());
        commands = update2.getCommands();
        assertFalse("Command queue is empty (update history).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (update history).", "addToGameHistory", command.getMethodName());

        // Test player 2 can do actions now.
        trainCardResponse = server.drawTrainCard(auth2);
        assertNull("Valid action rejected after next turn.", trainCardResponse.getException());
        server.drawTrainCard(auth2);

        // Test end turn, goes back to player 1
        serverResponse = server.endTurn(auth2);
        assertNull("Valid end turn rejected (Player 2).", serverResponse.getException());
        trainCardResponse = server.drawTrainCard(auth1);
        assertNull("Valid action rejected after turn goes back to player 1.", trainCardResponse.getException());
    }

    @Test
    public void testDrawDestinationCards() {
        // *** SET UP ***
        ServerFacade server = ServerFacade.getInstance();

        UserData data = new UserData("DestCardTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();

        data = new UserData("DestCardTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        Lobby lobby = new Lobby("Dest Card Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        String lobbyID = joinLobbyResponse.getLobby().getId();
        server.joinLobby(lobbyID, auth2);
        ClientUpdate update1 = server.updateClient(null, auth1);
        String lastID1 = update1.getLastCommandID();
        ClientUpdate update2 = server.updateClient(null, auth2);
        String lastID2 = update2.getLastCommandID();
        StartGameResponse startGameResponse = server.startGame(lobbyID, auth1);

        List<DestinationCard> cards1 = startGameResponse.getDestCardOptions().getDestinationCards();
        update2 = server.updateClient(lastID2, auth2);
        Queue<Command> commands = update2.getCommands();
        Command command = commands.remove();
        command.decode();
        ChoiceDestinationCards cards2Choice = (ChoiceDestinationCards)command.getParameters()[2];
        List<DestinationCard> cards2 = cards2Choice.getDestinationCards();
        server.discardDestinationCard(cards1.get(0), auth1);
        server.discardDestinationCard(cards2.get(0), auth2);
        // *** END SET UP ***

        // Test cannot draw destination cards when not your turn.
        DestinationCardResponse destResponse = server.drawDestinationCards(auth2);
        assertNotNull("Invalid draw destination cards (not my turn).", destResponse.getException());
        assertNull("Destination cards returned on invalid draw destination cards (not my turn).", destResponse.getCards());

        // Test cannot draw destination cards after drawing train cards.
        server.drawTrainCard(auth1);
        destResponse = server.drawDestinationCards(auth1);
        assertNotNull("Invalid draw destination cards (drew a train card).", destResponse.getException());
        assertNull("Destination cards returned on invalid draw destination cards (drew a train card).", destResponse.getCards());
        server.drawTrainCard(auth1);
        server.endTurn(auth1);

        update1 = server.updateClient(lastID1, auth1);
        lastID1 = update1.getLastCommandID();
        update2 = server.updateClient(lastID2, auth2);
        lastID2 = update2.getLastCommandID();

        // Test valid draw destination cards.
        destResponse = server.drawDestinationCards(auth2);
        assertNull("Valid draw destination cards rejected.", destResponse.getException());
        assertNotNull("No destination cards received on valid draw destination cards.", destResponse.getCards());
        assertEquals("Incorrect number of cards received on draw destination cards.", destResponse.getCards().size(), 3);

        // Test update history for both players
        update1 = server.updateClient(lastID1, auth1);
        assertNull("Update client failed.", update1.getException());
        assertNotNull("Command queue not sent (update history).", update1.getCommands());
        assertNotNull("Last command ID not sent (update history).", update1.getLastCommandID());
        commands = update1.getCommands();
        assertFalse("Command queue is empty (update history).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (update history).", "addToGameHistory", command.getMethodName());
        command = commands.remove();
        assertEquals("Wrong command sent (draw destination cards - after update history).",
                "addPlayerDestinationCards", command.getMethodName());

        update2 = server.updateClient(lastID2, auth2);
        assertNull("Update client failed.", update2.getException());
        assertNotNull("Command queue not sent (update history).", update2.getCommands());
        assertNotNull("Last command ID not sent (update history).", update2.getLastCommandID());
        commands = update2.getCommands();
        assertFalse("Command queue is empty (update history).", commands.isEmpty());
        command = commands.remove();
        assertEquals("Wrong command sent (update history).", "addToGameHistory", command.getMethodName());

        // TODO: Test able to discard 2 cards
    }

    @Test
    public void testClaimRoute() {
        // *** SET UP ***
        ServerFacade server = ServerFacade.getInstance();

        UserData data = new UserData("DestCardTest", "password");
        LoginResponse response = server.register(data);
        String auth1 = response.getAuthToken();

        data = new UserData("DestCardTest2", "password");
        response = server.register(data);
        String auth2 = response.getAuthToken();

        Lobby lobby = new Lobby("Dest Card Test", 2);
        JoinLobbyResponse joinLobbyResponse = server.createLobby(lobby, auth1);
        String lobbyID = joinLobbyResponse.getLobby().getId();
        server.joinLobby(lobbyID, auth2);
        ClientUpdate update1 = server.updateClient(null, auth1);
        String lastID1 = update1.getLastCommandID();
        ClientUpdate update2 = server.updateClient(null, auth2);
        String lastID2 = update2.getLastCommandID();
        StartGameResponse startGameResponse = server.startGame(lobbyID, auth1);

        List<DestinationCard> cards1 = startGameResponse.getDestCardOptions().getDestinationCards();
        update2 = server.updateClient(lastID2, auth2);
        Queue<Command> commands = update2.getCommands();
        Command command = commands.remove();
        command.decode();
        ChoiceDestinationCards cards2Choice = (ChoiceDestinationCards)command.getParameters()[2];
        List<DestinationCard> cards2 = cards2Choice.getDestinationCards();
        server.discardDestinationCard(cards1.get(0), auth1);
        server.discardDestinationCard(null, auth2);
        // *** END SET UP ***

        // TODO: After route names are updated, test claiming one.
    }
}
