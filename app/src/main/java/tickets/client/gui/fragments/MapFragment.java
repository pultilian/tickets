package tickets.client.gui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import tickets.client.gui.activities.R;
import tickets.client.gui.presenters.GameMapPresenter;
import tickets.client.gui.presenters.IGameMapPresenter;
import tickets.client.gui.presenters.IHolderActivity;
import tickets.client.gui.presenters.IHolderGameMapFragment;
import tickets.client.gui.views.MapView;

/**
 * Created by Pultilian on 3/4/2018.
 */

public class MapFragment extends Fragment implements IHolderGameMapFragment {
    IGameMapPresenter presenter;
    MapView mGameMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GameMapPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mGameMap = view.findViewById(R.id.gameMap);
        mGameMap.setPresenter(this.presenter);
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
//        Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkUpdate() {
        Log.d("Drawing", "checkUpdate on MapFragment called");
        if (this.isVisible()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mGameMap.setClaimedRoutes(presenter.getClaimedRoutes());
                }
            });
        }
        return;
    }

    @Override
    public void displayChooseColorDialog(List<String> colors) {
        mGameMap.displayChooseColorDialog(colors);
    }

    @Override
    public void clearSelectedCities() {
        mGameMap.clearSelectedCities();
    }
}
