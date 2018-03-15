package tickets.client.gui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.GameInfoPresenter;
import tickets.common.PlayerInfo;

/**
 * Created by Pultilian on 3/4/2018.
 */

public class GameInfoFragment extends Fragment {
    private RecyclerView playersInfo;
    private RecyclerView gameHistory;
    private GameInfoPresenter presenter;
    private RecyclerView.LayoutManager playerInfoManager;
    private RecyclerView.LayoutManager gameHistoryManager;
    private PlayersInfoAdapter playersInfoAdapter;
    private GameHistoryAdapter gameHistoryAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_info, container, false);
        presenter = new GameInfoPresenter();
        playersInfo = view.findViewById(R.id.players_info);
        gameHistory = view.findViewById(R.id.game_history);
        playerInfoManager = new LinearLayoutManager(this.getContext());
        gameHistoryManager = new LinearLayoutManager(this.getContext());
        playersInfo.setLayoutManager(playerInfoManager);
        gameHistory.setLayoutManager(gameHistoryManager);
        playersInfoAdapter = new PlayersInfoAdapter(this.getContext(), presenter.getPlayerInfo());
        gameHistoryAdapter = new GameHistoryAdapter(this.getContext(), presenter.getGameHistory());
        playersInfo.setAdapter(playersInfoAdapter);
        gameHistory.setAdapter(gameHistoryAdapter);

        return view;
    }

    class PlayersInfoAdapter extends RecyclerView.Adapter<GameInfoFragment.PlayersInfoHolder> {
        private LayoutInflater inflater;
        private List<PlayerInfo> playerInfoList;

        public PlayersInfoAdapter(Context context, List<PlayerInfo> playerInfoList) {
            this.playerInfoList = playerInfoList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public PlayersInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.player_info_card, parent, false);
            return new PlayersInfoHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(PlayersInfoHolder holder, int position) {
            PlayerInfo item = playerInfoList.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return playerInfoList.size(); //cards.size(); //TODO: get cards initialized.
        }

    }

    class PlayersInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cardImage;
        TextView name;
        TextView points;
        TextView shipsLeft;
        TextView resourcesCount;
        TextView destinationCount;

        public PlayersInfoHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            cardImage = view.findViewById(R.id.race_image_card);
            name = view.findViewById(R.id.player_info_name);
            points = view.findViewById(R.id.player_info_points);
            shipsLeft = view.findViewById(R.id.player_info_ships_left);
            resourcesCount = view.findViewById(R.id.players_info_resource);
            destinationCount = view.findViewById(R.id.players_info_destination);
        }

        // Assigns values in the layout.
        void bind(PlayerInfo item) {
            name.setText(item.getFaction().getName());
            points.setText("Points " + Integer.toString(item.getScore()));
            shipsLeft.setText("Ships Left " + Integer.toString(item.getShipsLeft()));
            resourcesCount.setText("Resources " + Integer.toString(item.getTrainCardCount()));
            destinationCount.setText("Destination " + Integer.toString(item.getDestinationCardCount()));
            switch (item.getFaction().getName().toLowerCase()){
                case "altian":
                    cardImage.setImageResource(R.drawable.card_altian);
                    break;
                case "kit":
                    cardImage.setImageResource(R.drawable.card_kit);
                    break;
                case "murtoken":
                    cardImage.setImageResource(R.drawable.card_murtoken);
                    break;
                case "tacht":
                    cardImage.setImageResource(R.drawable.card_tacht);
                    break;
                case "pathian":
                    cardImage.setImageResource(R.drawable.card_pathian);
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            return;
        }
    }

    class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryHolder> {
        private LayoutInflater inflater;
        private List<String> gameHistory;

        public GameHistoryAdapter(Context context, List<String> gameHistory) {
            this.gameHistory = gameHistory;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public GameHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.game_history_list, parent, false);
            return new GameHistoryHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(GameHistoryHolder holder, int position) {
            String item = gameHistory.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return 0; //cards.size(); //TODO: get cards initialized.
        }

    }

    class GameHistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView message;

        public GameHistoryHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            message.findViewById(R.id.game_history_text);
        }

        // Assigns values in the layout.
        void bind(String item) {
            message.setText(item);
        }

        @Override
        public void onClick(View view) {
            return;
        }
    }
}
