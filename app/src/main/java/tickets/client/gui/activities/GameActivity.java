package tickets.client.gui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tickets.client.ModelFacade;
import tickets.client.gui.fragments.ChatFragment;
import tickets.client.gui.fragments.DestinationFragment;
import tickets.client.gui.fragments.GameInfoFragment;
import tickets.client.gui.fragments.MapFragment;
import tickets.client.gui.presenters.GamePresenter;
import tickets.client.gui.presenters.IHolderActivity;
import tickets.client.gui.presenters.IHolderGameActivity;
import tickets.common.DestinationCard;
import tickets.common.Faction;
import tickets.common.Game;
import tickets.common.HandTrainCard;
import tickets.common.Player;
import tickets.common.RouteColors;
import tickets.common.TrainCard;

/**
 * Activity for the Main Screen, It holds the player info, current face up cards, and
 * @author Dallin Pulsipher
 */
public class GameActivity extends AppCompatActivity implements IHolderGameActivity {

    //Face up cards
    private ImageView faceUpCard1;
    private ImageView faceUpCard2;
    private ImageView faceUpCard3;
    private ImageView faceUpCard4;
    private ImageView faceUpCard5;

    // draw piles
    private ImageView drawResourcePile;
    private ImageView drawDestinationPile;

    // player resources count
    private TextView blueCount;
    private TextView redCount;
    private TextView yellowCount;
    private TextView greenCount;
    private TextView whiteCount;
    private TextView blackCount;
    private TextView orangeCount;
    private TextView pinkCount;
    private TextView silverCount;

    // player info
    private TextView playerName;
    private ImageView playerIcon;
    private TextView playerRace;
    private TextView ships;
    private TextView points;

    // recycler view stuff for destination cards
    private RecyclerView destinationCards;
    private RecyclerView.LayoutManager destinationManager;
    private DestinationAdapter destinationAdapter;

    // buttons
    private ImageView mapButton;
    private ImageView chatButton;
    private ImageView gameButton;
    private ImageView logoutButton;

    // presenter
    private GamePresenter presenter;
    private Game game;

    /** InitVariables
     * initializes variables for the recyclerView
     * @post recyclerView is updated
     */
    public void initVariables() {
        destinationManager = new LinearLayoutManager(this);
        destinationCards.setLayoutManager(destinationManager);
        destinationAdapter = new DestinationAdapter(this, presenter.getPlayerDestinations());
        destinationCards.setAdapter(destinationAdapter);
    }

    /** AssignIDs
     * assigns the object to their respective android objects.
     */
    public void assignIDs() {
        faceUpCard1 = this.findViewById(R.id.card1);
        faceUpCard2 = this.findViewById(R.id.card2);
        faceUpCard3 = this.findViewById(R.id.card3);
        faceUpCard4 = this.findViewById(R.id.card4);
        faceUpCard5 = this.findViewById(R.id.card5);
        drawResourcePile = this.findViewById(R.id.draw_ship);
        drawDestinationPile = this.findViewById(R.id.draw_destination);
        blueCount = this.findViewById(R.id.blue_count);
        redCount = this.findViewById(R.id.red_count);
        yellowCount = this.findViewById(R.id.yellow_count);
        greenCount = this.findViewById(R.id.green_count);
        whiteCount = this.findViewById(R.id.white_count);
        blackCount = this.findViewById(R.id.black_count);
        orangeCount = this.findViewById(R.id.orange_count);
        pinkCount = this.findViewById(R.id.pink_count);
        silverCount = this.findViewById(R.id.silver_count);
        playerName = this.findViewById(R.id.player_name);
        playerIcon = this.findViewById(R.id.race_image);
        playerRace = this.findViewById(R.id.race);
        ships = this.findViewById(R.id.ships);
        points = this.findViewById(R.id.points);
        destinationCards = this.findViewById(R.id.player_destination_cards);
        mapButton = this.findViewById(R.id.button_map);
        chatButton = this.findViewById(R.id.button_chat);
        gameButton = this.findViewById(R.id.button_game);
        logoutButton = this.findViewById(R.id.button_logout);
        Faction currentPlayer = presenter.getCurrentPlayer().getPlayerFaction();
        playerName.setText(currentPlayer.getName());
        playerRace.setText(currentPlayer.getName());

        //Sets the image based on the info
        switch (currentPlayer.getColor().toString().toLowerCase()) {
            case "blue":
                playerIcon.setImageResource(R.drawable.race_altian);
                break;
            case "red":
                playerIcon.setImageResource(R.drawable.race_tacht);
                break;
            case "green":
                playerIcon.setImageResource(R.drawable.race_pathian);
                break;
            case "yellow":
                playerIcon.setImageResource(R.drawable.race_kit);
                break;
            case "black":
                playerIcon.setImageResource(R.drawable.race_murtoken);
                break;
        }
    }

    /** setClickListeners
     * sets the click listeners for the various buttons and their operations
     */
    public void setClickListeners() {
        faceUpCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.drawFaceUpTrainCard(0);
                return;
            }
        });
        faceUpCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.drawFaceUpTrainCard(1);
                return;
            }
        });
        faceUpCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.drawFaceUpTrainCard(2);
                return;
            }
        });
        faceUpCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.drawFaceUpTrainCard(3);
                return;
            }
        });
        faceUpCard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.drawFaceUpTrainCard(4);
                return;
            }
        });
        drawResourcePile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.drawDestinationCard();
                return;
            }
        });
        drawDestinationPile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.drawDestinationCard();
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = new DestinationFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
                return;
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelFacade.getInstance().addPlayerPoints(15);
                ModelFacade.getInstance().removePlayerShips(12);
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = new MapFragment();
                fragmentManager.beginTransaction()
                            .add(R.id.fragment_container, fragment)
                            .commit();
                return;
            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = new ChatFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
                return;
            }
        });
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = new GameInfoFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
                return;
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
    }

    /** setNumResourceCards
     * sets the players resource cards count. and updates the screen.
     */
    public void setNumResourceCards() {
        HandTrainCard cards = presenter.getPlayerHand();

        blueCount.setText(Integer.toString(cards.getCountForColor(RouteColors.Blue)));
        redCount.setText(Integer.toString(cards.getCountForColor(RouteColors.Red)));
        greenCount.setText(Integer.toString(cards.getCountForColor(RouteColors.Green)));
        orangeCount.setText(Integer.toString(cards.getCountForColor(RouteColors.Orange)));
        yellowCount.setText(Integer.toString(cards.getCountForColor(RouteColors.Yellow)));
        whiteCount.setText(Integer.toString(cards.getCountForColor(RouteColors.White)));
        blackCount.setText(Integer.toString(cards.getCountForColor(RouteColors.Black)));
        silverCount.setText(Integer.toString(cards.getCountForColor(RouteColors.Wild)));
        pinkCount.setText(Integer.toString(cards.getCountForColor(RouteColors.Purple)));
    }

    /** setFaceUpCards
     * sets the face up cards and updates the screen.
     */
    public void setFaceUpCards(){
        ImageView[] faceUpCard = {faceUpCard1,faceUpCard2,faceUpCard3,faceUpCard4,faceUpCard5};
        List<TrainCard> cards = presenter.getFaceUpCards();
        for(int i = 0; i < cards.size(); i++){
            switch (cards.get(i).getColor().toString().toLowerCase()){
                case "blue":
                    faceUpCard[i].setImageResource(R.drawable.resource_blue);
                    break;
                case "red":
                    faceUpCard[i].setImageResource(R.drawable.resource_red);
                    break;
                case "pink":
                    faceUpCard[i].setImageResource(R.drawable.resource_pink);
                    break;
                case "orange":
                    faceUpCard[i].setImageResource(R.drawable.resource_orange);
                    break;
                case "yellow":
                    faceUpCard[i].setImageResource(R.drawable.resource_yellow);
                    break;
                case "black":
                    faceUpCard[i].setImageResource(R.drawable.resource_black);
                    break;
                case "white":
                    faceUpCard[i].setImageResource(R.drawable.resource_white);
                    break;
                case "green":
                    faceUpCard[i].setImageResource(R.drawable.resource_green);
                    break;
                case "silver":
                    faceUpCard[i].setImageResource(R.drawable.resource_silver);
                    break;

            }
        }
    }

    /** UpdatePoints
     * updates current players points for the observers.
     */
    public void updatePoints(){
         points.setText(Integer.toString(presenter.getCurrentPlayer().getInfo().getScore()));
    }

    /** UpdateShips
     * updates the ships count for the observers.
     */
    public void updateShips(){
        ships.setText(Integer.toString(presenter.getCurrentPlayer().getInfo().getShipsLeft()));
    }

    /** onCreate
     * runs this when the gameActivity is started. It initializes variables
     * and performs initial setup.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        presenter = new GamePresenter(this);
        assignIDs();
        initVariables();
        updatePlayerTrainHand();
        updatePoints();
        updateShips();
        updateFaceUpCards();
        setClickListeners();

        // sets the destination fragment as the first
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new DestinationFragment();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();

    }

    /** on Window FocusChanged
     * sets certain parameters for the Observer for how the android screen will be formatted.
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /** Make Transition
     * make a transition to another activity upon request of the server.
     * @param toActivity
     */
    @Override
    public void makeTransition(IHolderActivity.Transition toActivity) {
        //what transitions should be made?
        //GOH
        return;
    }

    /** ToastMessage
     * displays a toast upon request from the server.
     * @param message
     */
    @Override
    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return;
    }

    /** ToastException
     * throws an exception from the server in the form of a toast
     * @param e
     */
    @Override
    public void toastException(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        return;
    }

    /** CheckUpdate
     * it is here for the next phase.
     */
    @Override
    public void checkUpdate() {
        //do nothing?
        return;
    }

    /** updateFaceUpCards
     * updates the face up cards upon request from the server. Attached to Observer
     */
    @Override
    public void updateFaceUpCards(){
        setFaceUpCards();
    }

    /** updatePlayerTrainHand
     * updates Players train cards upon request from the server.
     */
    @Override
    public void updatePlayerTrainHand(){
        setNumResourceCards();
    }

    /** updatePlayerDestHand
     * updates Player destination cards upon request from the server.
     */
    @Override
    public void updatePlayerDestHand(){
        destinationAdapter.notifyDataSetChanged();
    }

    class DestinationAdapter extends RecyclerView.Adapter<DestinationHolder> {
        private LayoutInflater inflater;
        private List<DestinationCard> cards;

        public DestinationAdapter(Context context, List<DestinationCard> cards) {
            inflater = LayoutInflater.from(context);
            this.cards = cards;
        }

        @Override
        public DestinationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.destination_layout, parent, false);
            return new DestinationHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(DestinationHolder holder, int position) {
            DestinationCard item = cards.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

    }

    class DestinationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView location1;
        TextView location2;
        TextView destinationScore;

        public DestinationHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            location1 = findViewById(R.id.location1);
            location2 = findViewById(R.id.location2);
            destinationScore = findViewById(R.id.destination_score);

        }

        // Assigns values in the layout.
        void bind(DestinationCard item) {
            location1.setText(item.toString());
            location2.setText(item.toString());
            destinationScore.setText(item.toString());
            return;
        }

        @Override
        public void onClick(View view) {
            return;
        }
    }

}
