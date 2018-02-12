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

        startGameButton = (Button) this.findViewById(R.id.start_game_button);
        leaveGame = (Button) this.findViewById(R.id.leave_game);

        lobbyPlayerList = (RecyclerView) findViewById(R.id.lobby_player_list);
        updateWindow = (RecyclerView) findViewById(R.id.lobby_update_list);

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

        updateUI();

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

    public void makeTransition(IHolderActivity.Transition toActivity){
        if(toActivity == IHolderActivity.Transition.toLobbyList) {
            Intent intent = new Intent(LobbyActivity.this, LobbyListActivity.class);
            startActivity(intent);
        }
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



    public void updateUI() {
        lobbyPlayerAdapter = new LobbyPlayerAdapter(this);
        lobbyPlayerList.setAdapter(lobbyPlayerAdapter);
        updateAdapter = new UpdateAdapter(this);
        updateWindow.setAdapter(updateAdapter);
    }


    class LobbyPlayerAdapter extends RecyclerView.Adapter<LobbyPlayerHolder> {
        private LayoutInflater inflater;
        private Player[] players;

        public LobbyPlayerAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            players = (Player[]) currentLobby.getPlayers().toArray();
        }

        @Override
        public LobbyPlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.lobby_list_item, parent, false);
            return new LobbyPlayerHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(LobbyPlayerHolder holder, int position) {
            Player item = players[position];
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return players.length;
        }

    }

    class LobbyPlayerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView playerName;
        TextView color;

        public LobbyPlayerHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            playerName = (TextView) view.findViewById(R.id.title);
            color = (TextView) view.findViewById(R.id.description);
        }

        // Assigns values in the layout.
        void bind(Player item) {
            playerName.setText(item.getPlayerId());
            color.setText("COLOR");
        }

        @Override
        public void onClick(View view) {

        }
    }

    class UpdateAdapter extends RecyclerView.Adapter<updateHolder> {
        private LayoutInflater inflater;
        private String[] updates;

        public UpdateAdapter(Context context) {
            updates = currentLobby.getHistory().toArray(new String[currentLobby.getHistory().size()]);
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
            String item = updates[position];
            holder.bind(item);
            return;
        }

        @Override
        public int getItemCount() {
            return updates.length;
        }

    }

    class updateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView updateText;

        public updateHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            updateText = (TextView) view.findViewById(R.id.lobby_update_item);
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
