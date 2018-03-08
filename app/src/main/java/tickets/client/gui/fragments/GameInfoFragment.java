package tickets.client.gui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.GamePresenter;

/**
 * Created by Pultilian on 3/4/2018.
 */

public class GameInfoFragment extends Fragment {
    private RecyclerView playersInfo;
    private RecyclerView gameHistory;
    private GamePresenter presenter;
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
        playersInfo = view.findViewById(R.id.players_info);
        gameHistory = view.findViewById(R.id.game_history);
        playerInfoManager = new LinearLayoutManager(this.getContext());
        gameHistoryManager = new LinearLayoutManager(this.getContext());
        playersInfo.setLayoutManager(playerInfoManager);
        gameHistory.setLayoutManager(gameHistoryManager);
        playersInfoAdapter = new PlayersInfoAdapter(this.getContext()); //TODO: Destination Cards
        gameHistoryAdapter = new GameHistoryAdapter(this.getContext()); //TODO: Destination Cards
        playersInfo.setAdapter(playersInfoAdapter);
        gameHistory.setAdapter(gameHistoryAdapter);

        return view;
    }

    class PlayersInfoAdapter extends RecyclerView.Adapter<GameInfoFragment.PlayersInfoHolder> {
        private LayoutInflater inflater;

        public PlayersInfoAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public PlayersInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.destination_layout, parent, false);
            return new PlayersInfoHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(PlayersInfoHolder holder, int position) {

            holder.bind();
        }

        @Override
        public int getItemCount() {
            return 0; //cards.size(); //TODO: get cards initialized.
        }

    }

    class PlayersInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public PlayersInfoHolder(View view) {
            super(view);
            view.setOnClickListener(this);


        }

        // Assigns values in the layout.
        void bind() {
            return;
        }

        @Override
        public void onClick(View view) {
            return;
        }
    }

    class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryHolder> {
        private LayoutInflater inflater;

        public GameHistoryAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public GameHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.destination_layout, parent, false);
            return new GameHistoryHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(GameHistoryHolder holder, int position) {

            holder.bind();
        }

        @Override
        public int getItemCount() {
            return 0; //cards.size(); //TODO: get cards initialized.
        }

    }

    class GameHistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public GameHistoryHolder(View view) {
            super(view);
            view.setOnClickListener(this);


        }

        // Assigns values in the layout.
        void bind() {
            return;
        }

        @Override
        public void onClick(View view) {
            return;
        }
    }
}
