
package tickets.client.gui.presenters;

//An interface that Android activities must implement to interact with Presenters
public interface IHolderGameActivity extends IHolderActivity {

    //If the specified activity cannot be reached, don't do anything (throw an exception?)
    //If the transition is valid,

    public void updateFaceUpCards();
    public void updatePlayerTrainHand();
    public void updatePlayerDestHand();
    public void updatePoints();
    public void updateShips();
}