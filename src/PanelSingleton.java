import java.awt.CardLayout;

public class PanelSingleton {
	private static PanelSingleton instance;
	String previousPanelName;
	
	private PanelSingleton() {	
	
	}
	
	public static PanelSingleton getInstance() {
		if (instance == null) 
			instance = new PanelSingleton();

		return instance;
	}
	
	public void currentPanel(String panel) {
		previousPanelName = panel;
	}
	
	public String previousPanel() {
		return previousPanelName;
	}
}
