import java.awt.CardLayout;

public class PanelSingleton {
	private static PanelSingleton instance;
	String previousPanelName;
	int height;
	int width;
	
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
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
}
