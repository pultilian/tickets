package tickets.client.gui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tickets.client.ClientFacade;
import tickets.client.gui.presenters.GameSummaryPresenter;
import tickets.common.DestinationCard;
import tickets.common.PlayerSummary;

/**
 * Created by Pultilian on 3/26/2018.
 */

public class GameSummaryActivity extends AppCompatActivity {
    private Button endButton;
    private RecyclerView gameSummary;
    private RecyclerView.LayoutManager gameSummaryManager;
    private GameSummaryAdapter gameSummaryAdapter;
    private GameSummaryPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_summary);

        presenter = new GameSummaryPresenter();

        endButton = findViewById(R.id.button_end_game);
        gameSummaryManager = new LinearLayoutManager(this);
        gameSummary.setLayoutManager(gameSummaryManager);
        gameSummaryAdapter = new GameSummaryAdapter(this, presenter.getSummary());

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameSummaryActivity.this, LobbyListActivity.class);
                startActivity(intent);
            }
        });
    }
    class GameSummaryAdapter extends RecyclerView.Adapter<GameSummaryHolder> {
        private LayoutInflater inflater;
        private List<PlayerSummary> players;

        public GameSummaryAdapter(Context context, List<PlayerSummary> players) {
            inflater = LayoutInflater.from(context);
            this.players = players;
        }

        @Override
        public GameSummaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.player_summary_list, parent, false);
            return new GameSummaryHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(GameSummaryHolder holder, int position) {
            PlayerSummary item = players.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return players.size();
        }

    }

    class GameSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView faction;
        TextView playerName;
        TextView shipsLeft;
        TextView shipPoints;
        TextView successDest;
        TextView failDest;
        TextView totalPoints;
        ImageView longestRoute;


        public GameSummaryHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            faction = findViewById(R.id.faction);
            playerName = findViewById(R.id.player_name_summary);
            shipsLeft = findViewById(R.id.ships_left_summary);
            shipPoints = findViewById(R.id.ship_points_summary);
            successDest = findViewById(R.id.success_dest);
            failDest = findViewById(R.id.fail_dest);
            totalPoints = findViewById(R.id.total_points);
            longestRoute = findViewById(R.id.longest_route);

        }

        // Assigns values in the layout.
        void bind(PlayerSummary item) {
            playerName.setText(item.getPlayerName().toString());
            shipsLeft.setText(Integer.toString(item.getShipsLeft()));
            shipPoints.setText(Integer.toString(item.getShipPoints()));
            successDest.setText(Integer.toString(item.getSuccessDestPoints()));
            failDest.setText(Integer.toString(item.getFailDestPoints()));
            int totalPointsCalc = item.getShipPoints() + item.getSuccessDestPoints() - item.getFailDestPoints();
            totalPoints.setText(totalPointsCalc);
            if(item.isLongestRoute()){
                longestRoute.setImageResource(R.drawable.longest_route);
            }

            switch(item.getFaction().toLowerCase()){
                case "altian":
                    faction.setImageResource(R.drawable.race_altian);
                    break;
                case "murtoken":
                    faction.setImageResource(R.drawable.race_murtoken);
                    break;
                case "pathian":
                    faction.setImageResource(R.drawable.race_pathian);
                    break;
                case "kit":
                    faction.setImageResource(R.drawable.race_kit);
                    break;
                case "tacht":
                    faction.setImageResource(R.drawable.race_tacht);
                    break;
            }
            return;
        }

        @Override
        public void onClick(View view) {
            return;
        }
    }

}
