package tickets.client.gui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.DestinationPresenter;
import tickets.common.DestinationCard;


public class DestinationFragment extends Fragment {
    private LinearLayout card1;
    private LinearLayout card2;
    private LinearLayout card3;
    private TextView card1Location1;
    private TextView card1Score;
    private TextView card1Location2;
    private TextView card2Location1;
    private TextView card2Score;
    private TextView card2Location2;
    private TextView card3Location1;
    private TextView card3Score;
    private TextView card3Location2;
    private Button continueButton;

    private DestinationPresenter presenter;
    private boolean button1Selected;
    private boolean button2Selected;
    private boolean button3Selected;
    private List<DestinationCard> destinationCards;
    private Boolean[] selectedButtons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1Selected = false;
        button2Selected = false;
        button3Selected = false;

        selectedButtons = new Boolean[3];

        selectedButtons[0] = button1Selected;
        selectedButtons[1] = button2Selected;
        selectedButtons[2] = button3Selected;

        //TODO: get 3 destination cards

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);
        presenter = new DestinationPresenter();

        card1 = view.findViewById(R.id.dest_card1);
        card2 = view.findViewById(R.id.dest_card2);
        card3 = view.findViewById(R.id.dest_card3);
        card1Location1 = view.findViewById(R.id.card1_location1);
        card1Location2 = view.findViewById(R.id.card1_location2);
        card1Score = view.findViewById(R.id.card1_destination_score);
        card2Location1 = view.findViewById(R.id.card2_location1);
        card2Location2 = view.findViewById(R.id.card2_location2);
        card2Score = view.findViewById(R.id.card2_destination_score);
        card3Location1 = view.findViewById(R.id.card3_location1);
        card3Location2 = view.findViewById(R.id.card3_location2);
        card3Score = view.findViewById(R.id.card3_destination_score);
        continueButton = view.findViewById(R.id.button_continue);

        destinationCards = presenter.getDestinationCards();

        card1Location1.setText(destinationCards.get(0).getFirstCity());
        card1Location2.setText(destinationCards.get(0).getSecondCity());
        card1Score.setText(Integer.toString(destinationCards.get(0).getValue()));
        if(destinationCards.size() == 1){
            card2.setAlpha(0);
            card2.setEnabled(false);
            card3.setAlpha(0);
            card3.setEnabled(false);
        } else {
            card2Location1.setText(destinationCards.get(1).getFirstCity());
            card2Location2.setText(destinationCards.get(1).getSecondCity());
            card2Score.setText(Integer.toString(destinationCards.get(1).getValue()));
            if (destinationCards.size() == 2) {
                card3.setAlpha(0);
                card3.setEnabled(false);
            } else {
                card3Location1.setText(destinationCards.get(2).getFirstCity());
                card3Location2.setText(destinationCards.get(2).getSecondCity());
                card3Score.setText(Integer.toString(destinationCards.get(2).getValue()));
            }
        }
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = 0;
                List<DestinationCard> cardsToDiscard = new ArrayList<>();

                for (int i = 0; i < destinationCards.size(); i++) {
                    if (selectedButtons[i] == true) {
                        check++;
                    } else {
                        cardsToDiscard.add(destinationCards.get(i));
                    }
                }
                if(check > 0){
                presenter.chooseDestinationCards(cardsToDiscard);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new MapFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
                } else {
                    Toast.makeText(getActivity(),"You cannot discard all cards",Toast.LENGTH_SHORT).show();
                }
            }
        });

        card1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                button1Selected = !button1Selected;
                if(button1Selected){
                    selectedButtons[0] = button1Selected;
                    card1.setBackgroundResource(R.drawable.destination_card_selected);
                } else {
                    card1.setBackgroundResource(R.drawable.destination_card);
                }
            }
        });

        card2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                button2Selected = !button2Selected;
                selectedButtons[1] = button2Selected;
                if(button2Selected){
                    card2.setBackgroundResource(R.drawable.destination_card_selected);
                } else {
                    card2.setBackgroundResource(R.drawable.destination_card);
                }
            }
        });

        card3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                button3Selected = !button3Selected;
                if(button3Selected){
                    selectedButtons[2] = button3Selected;
                    card3.setBackgroundResource(R.drawable.destination_card_selected);
                } else {
                    card3.setBackgroundResource(R.drawable.destination_card);
                }
            }
        });

        return view;
    }
}