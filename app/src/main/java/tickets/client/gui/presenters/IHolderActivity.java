
package tickets.client.gui.presenters;

//An interface that Android activities must implement to interact with Presenters
public interface IHolderActivity {

    //If the specified activity cannot be reached, don't do anything (throw an exception?)
    //If the transition is valid, 
    public void makeTransition(Transition toActivity);
    public enum Transition {
        toLogin, toLobbyList, toLobby, toGame, toGameSummary, toDestinationFragment
    }

    public void toastMessage(String message);
    public void toastException(Exception e);

    public void checkUpdate();
}