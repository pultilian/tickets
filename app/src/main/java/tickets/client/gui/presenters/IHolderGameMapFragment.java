package tickets.client.gui.presenters;

import java.util.List;

public interface IHolderGameMapFragment extends IHolderActivity {
    void displayChooseColorDialog(List<String> colors);
    void clearSelectedCities();
}
