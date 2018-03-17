package tickets.client.commandline;

public class Main {

	public static void main(String[] args) {
		CommandlineView activeView = new CL_LoginView();
		while (activeView != null){
			activeView = activeView.display();
		}
	}
	
}