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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.GamePresenter;
import tickets.common.DestinationCard;

/**
 * Created by Pultilian on 3/4/2018.
 */

public class DestinationFragment extends Fragment {
    private TextView location1Left;
    private TextView location1Middle;
    private TextView location1Right;
    private TextView locatioion2Left;
    private TextView location2Middle;
    private TextView location2Right;
    private TextView scoreLeft;
    private TextView scoreMiddle;
    private TextView scoreRight;
    private Button continueButton;
    private GamePresenter presenter;
    private RelativeLayout button1;
    private RelativeLayout button2;
    private RelativeLayout button3;
    private boolean button1Selected;
    private boolean button2Selected;
    private boolean button3Selected;
    private List<DestinationCard> destinationCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1Selected = false;
        button2Selected = false;
        button3Selected = false;

        //TODO: get 3 destination cards

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        location1Left = view.findViewById(R.id.location1_left);
        location1Left = view.findViewById(R.id.location2_left);
        location1Left = view.findViewById(R.id.location1_middle);
        location1Left = view.findViewById(R.id.location2_middle);
        location1Left = view.findViewById(R.id.location1_right);
        location1Left = view.findViewById(R.id.location2_right);
        location1Left = view.findViewById(R.id.destination_score_left);
        location1Left = view.findViewById(R.id.destination_score_middle);
        location1Left = view.findViewById(R.id.destination_score_right);
        continueButton = view.findViewById(R.id.button_continue);

        button1 = view.findViewById(R.id.dest_card_1);
        button2 = view.findViewById(R.id.dest_card_2);
        button3 = view.findViewById(R.id.dest_card_3);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1Selected ^= true;
            }
        });

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                button2Selected ^= true;
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                button3Selected ^= true;
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}