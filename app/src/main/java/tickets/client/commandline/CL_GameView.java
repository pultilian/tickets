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
import tickets.common.DestinationCard;
import tickets.common.PlayerInfo;
import tickets.common.RouteColors;
import tickets.common.TrainCard;

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
                break;
            case 7:
				goToChat();
				break;
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

	private void discardDestinationCards(int canDiscard) {
        boolean discarded = false;
        List<DestinationCard> destinationCards = destinationPresenter.getDestinationCards();
        while(! discarded) {
            List<DestinationCard> discard = new ArrayList<>();
            for (int i = 0; i < canDiscard; i++) {
                System.out.println("Choose " + Integer.toString(canDiscard) + " cards to discard: ");
                printDestinationCards(destinationCards);
                System.out.println("Type \'x\' when you no longer wish to discard.");
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                try {
                    System.out.print("Discard: ");
                    int toDiscard = Integer.parseInt(input.readLine()) - 1;
                    discard.add(destinationCards.remove(toDiscard));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            try {
                destinationPresenter.chooseDestinationCards(discard);
                discarded = true;
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
                discarded = false;
            }
        }
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
        presenter.drawTrainCard();
    }

    private void drawDestinationCards() {
        presenter.drawDestinationCard();
        discardDestinationCards(2);
    }

    private void printPlayerInfo(){
        System.out.println("*Player Stats*");
        List<PlayerInfo> info = gameInfoPresenter.getPlayerInfo();
        for(int i = 0; i < info.size(); i++) {
            PlayerInfo player = info.get(i);
            if(i == gameInfoPresenter.getCurrentTurn()) {
                System.out.print("**");
            }
            String nameString = player.getName() + " - " + player.getFaction().getName();
            nameString += "(" + player.getFaction().getColor() + ")";
            System.out.println(nameString);
            System.out.println("  score: " + player.getScore());
            System.out.println("  ships: " + player.getShipsLeft());
            System.out.println("  resources: " + player.getTrainCardCount());
            System.out.println("  destinations: " + player.getDestinationCardCount());
        }
        System.out.println("*Match history*");
        for (String hist : gameInfoPresenter.getGameHistory())
            System.out.println("  " + hist);
        System.out.println();
    }

    private void goToChat() {
        System.out.println("*Game Chat*");
        int choice = 4;
        List<String> chat = gameChatPresenter.getChatHistory();
        int lastMessage = Math.max(chat.size() - 6, 0);
        while(choice != 3) {
            for (int i = lastMessage; i < lastMessage + 5; i++) {
                if (i < chat.size())
                    System.out.println("  " + chat.get(i));
            }
            System.out.println("  1. See previous 10 messages");
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
                    lastMessage = Math.max(lastMessage - 5, 0);
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
