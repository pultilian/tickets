package tickets.client.gui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.GameMapPresenter;
import tickets.client.gui.presenters.IGameMapPresenter;
import tickets.client.gui.presenters.IHolderActivity;
import tickets.client.gui.views.MapView;

/**
 * Created by Pultilian on 3/4/2018.
 */

public class MapFragment extends Fragment implements IHolderActivity {
    IGameMapPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GameMapPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        MapView map = view.findViewById(R.id.gameMap);
        map.setPresenter(this.presenter);
        return view;
    }

    @Override
    public void makeTransition(IHolderActivity.Transition toActivity) {
        // this shouldn't be called on this fragment...
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void toastException(Exception e) {
        Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkUpdate() {
        // TODO: what updates are needed?
        //  a route has been claimed by a player
    }
}
