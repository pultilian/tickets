//package tickets.client.gui.views;
//
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.Spinner;
//
//import tickets.client.gui.activities.LobbyListActivity;
//import tickets.client.gui.activities.R;
//
//public class CreateLobbyDialog extends DialogFragment {
//    private String setPlayers;
//    private String setLobbyName;
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        builder.setView(inflater.inflate(R.layout.create_lobby_dialog, null))
//                .setTitle("Create Lobby")
//                .setPositiveButton("create", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Create the Lobby object
//                        LobbyListActivity parent = (LobbyListActivity) getActivity();
//                        parent.createLobby(setPlayers, Integer.parseInt(setLobbyName));
//                        return;
//                    }
//                })
//                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//
//                        //do nothing
//                        return;
//                    }
//                });
//
//        Spinner spinner = getActivity().findViewById(R.id.num_players);
//        if (spinner == null) {
//            throw new RuntimeException("Spinner is null");
//        }
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.lobby_players, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                setPlayers = (String) adapterView.getItemAtPosition(i);
//                return;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                setPlayers = null;
//            }
//        });
//
//        EditText text = getActivity().findViewById(R.id.lobby_name);
//        text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                return;
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                setLobbyName = charSequence.toString();
//                return;
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                return;
//            }
//        });
//
//        return builder.create();
//    }
//}