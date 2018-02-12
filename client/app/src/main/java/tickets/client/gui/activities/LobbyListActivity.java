package tickets.client.gui.activities;

import java.util.UUID;

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
import tickets.client.gui.presenters.LobbyListPresenter;
import tickets.common.Lobby;

/**
 * Created by Pultilian on 2/10/2018.
 */

public class LobbyListActivity extends AppCompatActivity implements IHolderActivity {

    private Button joinButton;
    private Button logoutButton;
    private Button createGameButton;
    private RecyclerView lobbyList;
    private RecyclerView.LayoutManager lobbyListManager;
    private LobbyListAdapter lobbyListAdapter;
    private LobbyListPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new LobbyListPresenter(this);
        setContentView(R.layout.activity_lobby_list);

        joinButton = (Button) this.findViewById(R.id.join);
        logoutButton = (Button) this.findViewById(R.id.log_out);
        createGameButton = (Button) this.findViewById(R.id.create_game);
        lobbyList = (RecyclerView) findViewById(R.id.lobby_list);

        lobbyList.setLayoutManager(new LinearLayoutManager(this));
        lobbyListManager = new LinearLayoutManager(this);
        lobbyList.setLayoutManager(lobbyListManager);

        joinButton.setEnabled(false);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.joinLobby(UUID.randomUUID().toString());
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
                //
                return;
            }
        });


        updateUI();
        return;
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

    public void makeTransition(Transition toActivity){
        if(toActivity == Transition.toLobbyList) {
            Intent intent = new Intent(this, LobbyListActivity.class);
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
    }

    @Override
    //from IHolderActivity
    public void toastException(Exception var1){
        Context context = getApplicationContext();
        CharSequence text = var1.getMessage();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    void updateUI() {
        lobbyListAdapter = new LobbyListAdapter(this);
        lobbyList.setAdapter(lobbyListAdapter);
    }


    class LobbyListAdapter extends RecyclerView.Adapter<LobbyListHolder> {
        private Lobby[] curLobbyList;
        private LayoutInflater inflater;

        public LobbyListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            curLobbyList = (Lobby[]) presenter.getLobbyList().values().toArray();
        }

        @Override
        public LobbyListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.lobby_list_item, parent, false);
            return new LobbyListHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(LobbyListHolder holder, int position) {
            Lobby item = curLobbyList[position];
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return curLobbyList.length;
        }

    }

    class LobbyListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gameTitle;
        TextView numPlayers;

        public LobbyListHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            gameTitle = (TextView) view.findViewById(R.id.title);
            numPlayers = (TextView) view.findViewById(R.id.description);
        }

        // Assigns values in the layout.
        void bind(Lobby item) {
            gameTitle.setText(item.getName());
            numPlayers.setText(item.getCurrentMembers() + "/" + item.getMaxMembers());
        }

        @Override
        public void onClick(View view) {
            joinButton.setEnabled(true);

        }
    }
}
