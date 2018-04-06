package tickets.client.gui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.GameChatPresenter;
import tickets.client.gui.presenters.GamePresenter;
import tickets.client.gui.presenters.IHolderActivity;
import tickets.client.gui.presenters.IHolderGameChatFragment;
import tickets.common.DestinationCard;


/**
 * Created by Pultilian on 3/4/2018.
 */

public class ChatFragment extends Fragment implements IHolderGameChatFragment {
    private RecyclerView chatScreen;
    private EditText chatAdd;
    private Button sendButton;
    private RecyclerView.LayoutManager chatManager;
    private ChatAdapter chatAdapter;
    private GameChatPresenter presenter;

    public void updateChat(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatAdapter.notifyItemInserted(chatAdapter.getItemCount() - 1);
            }
        });
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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        return;
    }

    /** ToastException
     * throws an exception from the server in the form of a toast
     * @param e
     */
    @Override
    public void toastException(Exception e) {
//        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void checkUpdate() {
        return;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        presenter = new GameChatPresenter(this);
        chatScreen = view.findViewById(R.id.chat_list);
        chatAdd = (EditText) view.findViewById(R.id.chat_add);
        sendButton = (Button) view.findViewById(R.id.send_button);

        chatManager = new LinearLayoutManager(this.getContext());
        chatScreen.setLayoutManager(chatManager);
        chatAdapter = new ChatAdapter(this.getContext(), presenter.getChatHistory());
        chatScreen.setAdapter(chatAdapter);
        sendButton.setEnabled(false);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addToChat(chatAdd.getText().toString());
                chatAdd.setText("");
                sendButton.setEnabled(false);
            }
        });

        chatAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!chatAdd.getText().equals("")){
                    sendButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    class ChatAdapter extends RecyclerView.Adapter<ChatFragment.ChatHolder> {
        private LayoutInflater inflater;
        private List<String> chatMessages;

        public ChatAdapter(Context context, List<String> chatMessages) {
            inflater = LayoutInflater.from(context);
            this.chatMessages = chatMessages;
        }

        @Override
        public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.game_history_list, parent, false);
            return new ChatHolder(view);
        }

        // Grabs an individual row and assigns its values through the holder class.
        @Override
        public void onBindViewHolder(ChatHolder holder, int position) {
            holder.bind(chatMessages.get(position));
        }

        @Override
        public int getItemCount() {
            return chatMessages.size();
        }

    }

    class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView message;

        public ChatHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            message = view.findViewById(R.id.game_history_text);
        }

        // Assigns values in the layout.
        void bind(String message) {
            this.message.setText(message);
            return;
        }

        @Override
        public void onClick(View view) {
            return;
        }
    }
}
