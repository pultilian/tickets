package tickets.client.commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import tickets.client.TaskManager;
import tickets.client.gui.presenters.DestinationPresenter;
import tickets.client.gui.presenters.GameChatPresenter;
import tickets.client.gui.presenters.GameInfoPresenter;
import tickets.client.gui.presenters.GamePresenter;
import tickets.common.Command;
import tickets.common.DestinationCard;
import tickets.common.PlayerInfo;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.UserData;

public class CL_GameView extends CommandlineView {
	private GamePresenter presenter = new GamePresenter();
	private DestinationPresenter destinationPresenter = new DestinationPresenter(TaskManager.getInstance());
	private GameChatPresenter gameChatPresenter = new GameChatPresenter();
	private GameInfoPresenter gameInfoPresenter = new GameInfoPresenter();

	@Override
    public CommandlineView display(){
	    turnZero();
        return super.display();
    }

    private void turnZero(){
        System.out.println("*** You are in a game ***\n");
        discardDestinationCards(1);
        System.out.println();
    }

	@Override
	void printMenu() {
        System.out.println("*** You are in a game ***\n");
        System.out.println("  Select an option: ");
        System.out.println("    1. Check my resource cards");
        System.out.println("    2. Check my destination cards");
        System.out.println("    3. Take a face up resource card");
        System.out.println("    4. Draw a resource card from deck");
        System.out.println("    5. Draw destination cards");
        System.out.println("    6. See player info");
        System.out.println("    7. Go to chat");
        System.out.println("    8. Leave game");
        System.out.println();
	}

	@Override
	boolean handleUserInput(int in) {
		switch(in) {
            case 1:
                printResourceCards();
                break;
            case 2:
                printDestinationCards(presenter.getPlayerDestinations());
                break;
            case 3:
                takeFaceUpCard();
                break;
            case 4:
                drawResourceCard();
                break;
            case 5:
                drawDestinationCards();
                break;
            case 6:
                printPlayerInfo();
                return true;
            case 7:
				goToChat();
				return true;
            case 8:
                this.returnValue = new CL_LobbyListView();
                return true;
			default:
				System.out.println("Input choice not recognized.");
		}
		return false;
	}

    private void printResourceCards() {
        for (RouteColors color : RouteColors.values()) {
            int count = presenter.getPlayerHand().getCountForColor(color);
            if (count != 0)
                System.out.println("You have " + Integer.toString(count) + " " +
                    color.name() + " cards");
        }
        System.out.println();
    }

    //TODO: only supports single card discarding, so far
	private void discardDestinationCards(int canDiscard) {
        System.out.println("Choose " + Integer.toString(canDiscard)  + " cards to discard: ");
        List<DestinationCard> destinationCards = destinationPresenter.getDestinationCards();
        printDestinationCards(destinationCards);
        System.out.println("Type \'x\' when you' no longer wish to discard.");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Discard: ");
            int discard = Integer.parseInt(input.readLine());
            destinationPresenter.chooseDestinationCards(destinationCards.get(discard));
        } catch(IOException e) {
            e.printStackTrace();
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private void printDestinationCards(List<DestinationCard> destinationCards) {
        int i = 1;
        for (DestinationCard card : destinationCards) {
            String outString = "  " + Integer.toString(i) + ". " + card.getFirstCity();
            outString += " to " + card.getSecondCity() + " (";
            outString += Integer.toString(card.getValue()) + " points)";
            System.out.println(outString);
            i++;
        }
        System.out.println();
    }

    private void takeFaceUpCard() {
        System.out.println("Available resource cards: ");
        List<TrainCard> cards = presenter.getFaceUpCards();
        int i = 1;
        for (TrainCard card: cards) {
            System.out.println("  " + Integer.toString(i) + ". " + card.getColor().name());
            i++;
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Choose a resource card: ");
            int choice = Integer.parseInt(input.readLine());
            presenter.drawFaceUpTrainCard(choice - 1);
        } catch(IOException e) {
            e.printStackTrace();
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void drawResourceCard() {
        presenter.drawDestinationCard();
    }

    private void drawDestinationCards() {
        presenter.drawDestinationCard();
        discardDestinationCards(2);
    }

    private void printPlayerInfo(){
        System.out.println("*Game Info*");
        for(PlayerInfo info : gameInfoPresenter.getPlayerInfo()){
            String nameString = info.getName() + " - " + info.getFaction().getName();
            nameString += "(" + info.getFaction().getColor() + ")";
            System.out.println(nameString);
            System.out.println("  score: " + info.getScore());
            System.out.println("  ships: " + info.getShipsLeft());
            System.out.println("  resources: " + info.getTrainCardCount());
            System.out.println("  destinations: " + info.getDestinationCardCount());
        }
        System.out.println("Match history");
        for (String hist : gameInfoPresenter.getGameHistory())
            System.out.println("  " + hist);
        System.out.println();
    }

    private void goToChat() {
        System.out.println("*Game Chat*");
        int choice = 4;
        List<String> chat = gameChatPresenter.getChatHistory();
        int lastMessage = Math.min(chat.size() - 6, 0);
        while(choice != 3) {
            for (int i = lastMessage; i < lastMessage+5;  i++) {
                System.out.println("  " + chat.get(i));
            }
            System.out.println("  1. See 10 more messages");
            System.out.println("  2. Send a message");
            System.out.println("  3. Leave chat");

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.print("Your choice: ");
                choice = Integer.parseInt(input.readLine());
            } catch(IOException e) {
                e.printStackTrace();
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }
            switch(choice) {
                case 1:
                    lastMessage = lastMessage - 5;
                    break;
                case 2:
                    System.out.print("Your message: ");
                    try {
                        String message = input.readLine();
                        gameChatPresenter.addToChat(message);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Input choice not recognized.");
            }
        }
    }
}
