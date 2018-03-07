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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tickets.client.gui.presenters.IHolderActivity;
import tickets.client.gui.presenters.LobbyPresenter;
import tickets.common.Lobby;
import tickets.common.Player;

/**
 * Created by Pultilian on 2/10/2018.
 */

public class LobbyActivity extends AppCompatActivity implements IHolderActivity {

    //views
    private Button startGameButton;
    private Button leaveGame;
    private RecyclerView lobbyPlayerList;
    private RecyclerView updateWindow;
    private RecyclerView.LayoutManager lobbyPlayerManager;
    private LobbyPlayerAdapter lobbyPlayerAdapter;
    private RecyclerView.LayoutManager updateManager;
    private UpdateAdapter updateAdapter;

    //member variables
    private LobbyPresenter presenter;
    private Lobby currentLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        presenter = new LobbyPresenter(this);
        currentLobby = presenter.getLobby();

        startGameButton = this.findViewById(R.id.start_game_button);
        leaveGame = this.findViewById(R.id.leave_game);

        startGameButton.setEnabled(false);

        lobbyPlayerList = findViewById(R.id.lobby_player_list);
        updateWindow = findViewById(R.id.lobby_update_list);

        lobbyPlayerList.setLayoutManager(new LinearLayoutManager(this));
        updateWindow.setLayoutManager(new LinearLayoutManager(this));

        lobbyPlayerManager = new LinearLayoutManager(this);
        updateManager = new LinearLayoutManager(this);

        lobbyPlayerList.setLayoutManager(lobbyPlayerManager);
        updateWindow.setLayoutManager(updateManager);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startGame(currentLobby.getId());
                return;
            }
        });

        leaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.leaveLobby(currentLobby.getId());
                return;
            }
        });

        setUI();
        return;
    }

    public void setUI(){
        Lobby currentLobby = presenter.getLobby();
        List<Player> allPlayers = currentLobby.getPlayers();
        lobbyPlayerAdapter = new LobbyPlayerAdapter(this, allPlayers);
        lobbyPlayerList.setAdapter(lobbyPlayerAdapter);

        List<String> history = currentLobby.getHistory();
        updateAdapter = new UpdateAdapter(this, history);
        updateWindow.setAdapter(updateAdapter);
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
        return;
    }

    public void makeTransition(IHolderActivity.Transition toActivity){
        if(toActivity == IHolderActivity.Transition.toLobbyList) {
            Intent intent = new Intent(LobbyActivity.this, LobbyListActivity.class);
            startActivity(intent);
        }
        if(toActivity == Transition.toGame) {
            Intent intent = new Intent(LobbyActivity.this, GameActivity.class);
            startActivity(intent);
        }
        return;
    }

    @Override
    //from IHolderActivity
    public void toastMessage(String var1){
        Context context = getApplicationContext();
        CharSequence text = var1;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        return;
    }

    @Override
    //from IHolderActivity
    public void toastException(Exception var1){
        Context context = getApplicationContext();
        CharSequence text = var1.getMessage();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        return;
    }

    @Override
    //from IHolderActivity
    public void checkUpdate() {
        //update the Lobby History
        Lobby currentLobby = presenter.getLobby();
        List<Player> allPlayers = currentLobby.getPlayers();
        LobbyPlayerAdapter adapter = new LobbyPlayerAdapter(this, allPlayers);
        lobbyPlayerList.setAdapter(adapter);

        List<String> history = currentLobby.getHistory();
        updateAdapter = new UpdateAdapter(this, history);
        updateWindow.setAdapter(updateAdapter);
        return;
    }


    class LobbyPlayerAdapter extends RecyclerView.Adapter<LobbyPlayerHolder> {
        private LayoutInflater inflater;
        private List<Player> players;

        public LobbyPlayerAdapter(Context context, List<Player> setPlayers) {
            inflater = LayoutInflater.from(context);
            players = setPlayers;

            if(players.size() > 1 && players.size() < 6){
                startGameButton.setEnabled(true);
            } else {
                startGameButton.setEnabled(false);
            }
        }

        @Override
        public LobbyPlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.lobby_list_item, parent, false);
            return new LobbyPlayerHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(LobbyPlayerHolder holder, int position) {
            Player item = players.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return players.size();
        }

    }

    class LobbyPlayerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView playerName;
        TextView color;

        public LobbyPlayerHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            playerName = view.findViewById(R.id.title);
            color = view.findViewById(R.id.description);
        }

        // Assigns values in the layout.
        void bind(Player item) {
            playerName.setText(item.getPlayerFaction().getName());
            color.setText(item.getPlayerFaction().getColor().toString());
            return;
        }

        @Override
        public void onClick(View view) {
            //
            return;
        }
    }

    class UpdateAdapter extends RecyclerView.Adapter<updateHolder> {
        private LayoutInflater inflater;
        private List<String> updates;

        public UpdateAdapter(Context context, List<String> setHistory) {
            updates = setHistory;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public updateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.lobby_update_list, parent, false);
            return new updateHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(updateHolder holder, int position) {
            String item = updates.get(position);
            holder.bind(item);
            return;
        }

        @Override
        public int getItemCount() {
            return updates.size();
        }

    }

    class updateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView updateText;

        public updateHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            updateText = view.findViewById(R.id.lobby_update_item);
        }

        // Assigns values in the layout.
        void bind(String item) {
            updateText.setText(item);
            return;
        }

        @Override
        public void onClick(View view) {
            //
            return;
        }
    }
}
