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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tickets.client.gui.activities.GameActivity;
import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.GamePresenter;
import tickets.common.DestinationCard;

/**
 * Created by Pultilian on 3/4/2018.
 */

public class ChatFragment extends Fragment {
    private RecyclerView chatScreen;
    private EditText chatAdd;
    private Button sendButton;
    private RecyclerView.LayoutManager chatManager;
    private ChatAdapter chatAdapter;
    private GamePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatScreen = view.findViewById(R.id.chat_list);
        chatAdd = (EditText) view.findViewById(R.id.chat_add);
        sendButton = (Button) view.findViewById(R.id.send_button);

        chatManager = new LinearLayoutManager(this.getContext());
        chatScreen.setLayoutManager(chatManager);
        chatAdapter = new ChatAdapter(this.getContext()); //TODO: Destination Cards
        chatScreen.setAdapter(chatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    class ChatAdapter extends RecyclerView.Adapter<ChatFragment.ChatHolder> {
        private LayoutInflater inflater;

        public ChatAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.destination_layout, parent, false);
            return new ChatHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(ChatHolder holder, int position) {

            holder.bind();
        }

        @Override
        public int getItemCount() {
            return 0; //cards.size(); //TODO: get cards initialized.
        }

    }

    class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ChatHolder(View view) {
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
