package tickets.client.gui.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import tickets.client.gui.activities.LobbyListActivity;
import tickets.client.gui.activities.R;

public class CreateLobbyDialog extends DialogFragment {
    private String lobbyName = "";
    private int maxPlayers = -1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //capture the view to set listeners
        View view = inflater.inflate(R.layout.create_lobby_dialog, null);

        Spinner spinner = view.findViewById(R.id.set_players);
        if (spinner == null) {
            throw new RuntimeException("Spinner is null");
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.lobby_players,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // [0] == 2
                // [1] == 3
                // [3] == 4
                // [4] == 5
                CreateLobbyDialog.this.setMaxPlayers(i + 2);
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                CreateLobbyDialog.this.setMaxPlayers(-1);
                return;
            }
        });

        EditText text = view.findViewById(R.id.set_name);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CreateLobbyDialog.this.setLobbyName(charSequence.toString());
                return;
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        //AlertDialog Builder method to add title, positive and negative buttons
        builder.setView(view)
                .setTitle("Create Lobby")
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LobbyListActivity parent = (LobbyListActivity) getActivity();
                        // Initial check for valid input
                        if (maxPlayers != -1 && !lobbyName.equals("")) {
                            //Create the Lobby
                            parent.createLobby(lobbyName, maxPlayers);getActivity();
                        }
                        else {
                            parent.toastException(new Exception("Invalid lobby parameters"));
                        }
                        return;
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        // Dismiss the dialog - this should happen automatically
                        return;
                    }
                });

        return builder.create();
    }

    public void setLobbyName(String name) {
        lobbyName = name;
        return;
    }

    public void setMaxPlayers(int max) {
        maxPlayers = max;
        return;
    }
}