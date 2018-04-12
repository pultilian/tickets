package tickets.client.gui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tickets.client.gui.presenters.IHolderActivity;
import tickets.client.gui.presenters.LobbyListPresenter;
import tickets.common.Lobby;
import tickets.common.Game;

/**
 * Created by Pultilian on 2/10/2018.
 */

public class LobbyListActivity extends AppCompatActivity implements IHolderActivity {

    //Views
    private Button joinButton;
    private Button logoutButton;
    private Button createGameButton;
    private Button resumeButton;
    private RecyclerView lobbyList;
    private RecyclerView.LayoutManager lobbyListManager;
    private LobbyListAdapter lobbyListAdapter;
    private RecyclerView currentGamesList;
    private RecyclerView.LayoutManager currentGamesListManager;
    private CurrentGamesAdapter currentGamesAdapter;
    private LobbyListPresenter presenter;
    private EditText gameName;
    private EditText numPlayers;
    private String lobbyID;
    private String gameID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new LobbyListPresenter(this);
        setContentView(R.layout.activity_lobby_list);

        joinButton = this.findViewById(R.id.join);
        logoutButton = this.findViewById(R.id.log_out);
        createGameButton = this.findViewById(R.id.create_game);
        resumeButton = this.findViewById(R.id.resume_button);

        lobbyList = findViewById(R.id.lobby_list);
//        lobbyList.setLayoutManager(new LinearLayoutManager(this));
        lobbyListManager = new LinearLayoutManager(this);
        lobbyList.setLayoutManager(lobbyListManager);

        currentGamesList = findViewById(R.id.current_list);
        currentGamesListManager = new LinearLayoutManager(this);
        currentGamesList.setLayoutManager(currentGamesListManager);

        joinButton.setEnabled(false);
        createGameButton.setEnabled(false);
        resumeButton.setEnabled(false);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.joinLobby(lobbyID);
                return;
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.logout();
                return;
            }
        });

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lobbyName = gameName.getText().toString();
                String players = numPlayers.getText().toString();
                try {
                    int p = Integer.parseInt(players);
                    if(p > 1 && p < 6) {
                        presenter.createLobby(new Lobby(lobbyName, p));
                    } else {
                        toastMessage("You must have between 2-5 players.");
                    }
                }
                catch(NumberFormatException e) {
                    Toast.makeText(LobbyListActivity.this, "Invalid number of players", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lobbyID != null) presenter.resumeLobby(lobbyID);
                else if (gameID != null) presenter.resumeGame(gameID);
            }
        });

        gameName = findViewById(R.id.name_game);
        gameName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        numPlayers = findViewById(R.id.num_players);
        numPlayers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        setUI();
        checkButton();
        return;
    }

    public void setUI(){
        List<Lobby> lobbies = presenter.getLobbyList();
        lobbyListAdapter = new LobbyListAdapter(this, lobbies);
        lobbyList.setAdapter(lobbyListAdapter);

        List<Lobby> currentLobbies = presenter.getCurrentLobbies();
        List<Game> currentGames = presenter.getCurrentGames();
        currentGamesAdapter = new CurrentGamesAdapter(this, currentLobbies, currentGames);
        currentGamesList.setAdapter(currentGamesAdapter);
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

    public void makeTransition(Transition toActivity){
        if(toActivity == Transition.toLobby) {
            Intent intent = new Intent(this, LobbyActivity.class);
            startActivity(intent);
        }
        if(toActivity == Transition.toLogin) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        if(toActivity == Transition.toGame) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(GameActivity.RESUMED, true);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lobbyListAdapter.notifyDataSetChanged();
                currentGamesAdapter.notifyDataSetChanged();
            }
        });
    }

    class LobbyListAdapter extends RecyclerView.Adapter<LobbyListHolder> {
        private List<Lobby> lobbyList;
        private LayoutInflater inflater;

        public LobbyListAdapter(Context context, List<Lobby> setLobbies) {
            inflater = LayoutInflater.from(context);
            lobbyList = setLobbies;
        }

        @Override
        public LobbyListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.lobby_list_item, parent, false);
            return new LobbyListHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(LobbyListHolder holder, int position) {
            Lobby item = lobbyList.get(position);

            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return lobbyList.size();
        }

    }

    class LobbyListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gameTitle;
        TextView numPlayers;
        int maxPlayers;
        int curPlayers;
        String id;
        boolean lobbyInProgress;
        boolean gameInProgress;

        public LobbyListHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            gameTitle = view.findViewById(R.id.title);
            numPlayers = view.findViewById(R.id.description);
            maxPlayers = 0;
            id = null;
            lobbyInProgress = false;
            gameInProgress = false;
        }

        // Assigns values in the layout.
        void bind(Lobby item) {
            gameTitle.setText(item.getName());
            numPlayers.setText(item.getCurrentMembers() + "/" + item.getMaxMembers());
            maxPlayers = item.getMaxMembers();
            curPlayers = item.getCurrentMembers();
            id = item.getId();
        }

        // Assigns values in the layout for an in-progress lobby
        void bindForResume(Lobby item) {
            gameTitle.setText(item.getName());
            numPlayers.setText(item.getCurrentMembers() + "/" + item.getMaxMembers());
            id = item.getId();
            lobbyInProgress = true;
        }

        // Assigns values in the layout for an in-progress game.
        void bindForResume(Game item) {
            gameTitle.setText(item.getName());
            numPlayers.setText("IN PROGRESS");
            id = item.getGameId();
            gameInProgress = true;
        }

        @Override
        public void onClick(View view) {
            if(curPlayers < maxPlayers) {
                lobbyID = id;
                joinButton.setEnabled(true);
            }
            if (lobbyInProgress) {
                joinButton.setEnabled(false);
                resumeButton.setEnabled(true);
                gameID = null;
                lobbyID = id;
            }
            if (gameInProgress) {
                joinButton.setEnabled(false);
                resumeButton.setEnabled(true);
                gameID = id;
                lobbyID = null;
            }
        }
    }

    class CurrentGamesAdapter extends RecyclerView.Adapter<LobbyListHolder> {
        private List<Lobby> currentLobbies;
        private List<Game> currentGames;
        private LayoutInflater inflater;

        public CurrentGamesAdapter(Context context, List<Lobby> currentLobbies, List<Game> currentGames) {
            inflater = LayoutInflater.from(context);
            this.currentLobbies = currentLobbies;
            this.currentGames = currentGames;
        }

        @Override
        public LobbyListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.lobby_list_item, parent, false);
            return new LobbyListHolder(view);
        }

        @Override
        public void onBindViewHolder(LobbyListHolder holder, int position) {
            // Assign in-progress games first
            if (position < currentGames.size()) {
                Game item = currentGames.get(position);
                holder.bindForResume(item);
            }
            // Assign in-progress lobbies last
            else {
                Lobby item = currentLobbies.get(position - currentGames.size());
                holder.bindForResume(item);
            }
        }

        @Override
        public int getItemCount() {
            return (currentLobbies.size() + currentGames.size());
        }
    }

    void checkButton() {
        // Register Button
        if (gameName.getText().toString().length() != 0 &&
                numPlayers.getText().toString().length() != 0) {
            createGameButton.setEnabled(true);
        } else {
            createGameButton.setEnabled(false);
        }
    }

    public void createLobby(String lobbyName, int maxPlayers) {
        Lobby lobby = new Lobby(lobbyName, maxPlayers);
        presenter.createLobby(lobby);
        return;
    }
}
