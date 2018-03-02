package tickets.client.gui.activities;

import android.content.Context;
import android.os.Bundle;
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

import tickets.client.gui.presenters.GamePresenter;
import tickets.client.gui.presenters.IHolderActivity;
import tickets.common.DestinationCard;
import tickets.common.Game;
import tickets.common.Player;
import tickets.common.TrainCard;

/**
 * Created by Pultilian on 2/11/2018.
 */

public class GameActivity extends AppCompatActivity implements IHolderActivity {
    private ImageView faceUpCard1;
    private ImageView faceUpCard2;
    private ImageView faceUpCard3;
    private ImageView faceUpCard4;
    private ImageView faceUpCard5;
    private ImageView drawResourcePile;
    private ImageView drawDestinationPile;
    private TextView blueCount;
    private TextView redCount;
    private TextView yellowCount;
    private TextView greenCount;
    private TextView whiteCount;
    private TextView blackCount;
    private TextView orangeCount;
    private TextView pinkCount;
    private TextView silverCount;
    private TextView playerName;
    private ImageView playerIcon;
    private TextView playerRace;
    private TextView ships;
    private TextView points;
    private RecyclerView destinationCards;
    private RecyclerView.LayoutManager destinationManager;
    private DestinationAdapter destinationAdapter;
    private ImageView mapButton;
    private ImageView chatButton;
    private ImageView gameButton;
    private ImageView logoutButton;

    private GamePresenter presenter;
    private Game game;


    public void initVariables(){
        presenter = new GamePresenter(this);
        destinationManager = new LinearLayoutManager(this);
        destinationCards.setLayoutManager(destinationManager);
        destinationAdapter = new DestinationAdapter(this, null); //TODO: Destination Cards
        destinationCards.setAdapter(destinationAdapter);
    }


    public void assignIDs(){
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
        mapButton = this.findViewById(R.id.button_chat);
        mapButton = this.findViewById(R.id.button_game);
        mapButton = this.findViewById(R.id.button_logout);
    }

    public void setClickListeners(){
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
                return;
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Change fragment to the Map Canvas
                return;
            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Change fragment to Chat
                return;
            }
        });
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Change fragment to Game Info
                return;
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Log the User out.
                return;
            }
        });
    }

    public void setNumResourceCards(){
        int red = 0;
        int blue = 0;
        int yellow = 0;
        int green = 0;
        int white = 0;
        int black = 0;
        int orange = 0;
        int pink = 0;
        int silver = 0;

        List<TrainCard> cards = presenter.getPlayerHand();

        for(int i = 0; i < cards.size(); i++){
            String color = cards.get(i).getColor();

            switch (color){
                case "red":
                    red++;
                    break;
                case "blue":
                    blue++;
                    break;
                case "yellow":
                    yellow++;
                    break;
                case "green":
                    green++;
                    break;
                case "white":
                    white++;
                    break;
                case "black":
                    black++;
                    break;
                case "orange":
                    orange++;
                    break;
                case "pink":
                    pink++;
                    break;
                case "silver":
                    silver++;
                    break;
            }
        }

        redCount.setText(Integer.toString(red));
        yellowCount.setText(Integer.toString(yellow));
        blueCount.setText(Integer.toString(blue));
        greenCount.setText(Integer.toString(green));
        whiteCount.setText(Integer.toString(white));
        blackCount.setText(Integer.toString(black));
        orangeCount.setText(Integer.toString(orange));
        pinkCount.setText(Integer.toString(pink));
        silverCount.setText(Integer.toString(silver));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initVariables();
        assignIDs();
<<<<<<< HEAD
        setClickListeners();
=======
>>>>>>> 92fa79b4f8447337a6b7df9558787756f0073cca

        //TODO: way to set the starting images and get a list of the faceUpCards
        //Yes



    }

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

    @Override
    public void makeTransition(IHolderActivity.Transition toActivity) {
        //what transitions should be made?
        return;
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void toastException(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void checkUpdate() {
        //do nothing?
        return;
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
        ImageView card;

        public DestinationHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            card = findViewById(R.id.card_destination);
        }

        // Assigns values in the layout.
        void bind(DestinationCard item) {

            return;
        }

        @Override
        public void onClick(View view) {
            return;
        }
    }

}
