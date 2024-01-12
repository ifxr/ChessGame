import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeaderboardPanel extends JPanel implements ActionListener {
	private PanelSingleton panelSingleton;			// Used to attain frame size components and access previous accessed panel
	CardLayout cardLayout;							// Used to traverse to the different panels in the container
	
	JPanel bottomPnl;								// Used to layout the necessary buttons
	
	JButton backBtn;								// Used to go back to main menu
	Leaderboard leaderboard;
	
	LeaderboardPanel(CardLayout cardLayout){
		this.cardLayout = cardLayout;						
		panelSingleton = PanelSingleton.getInstance();	
				
		bottomPnl = new JPanel();
		
		// Necessary to set the bottom panel bounds
		int width = panelSingleton.getWidth();
		int height = panelSingleton.getHeight();
		bottomPnl.setBounds((int)(width * 0.1), (int)(height * 0.1)-15, 
    			(width - ((int)(width * 0.1)*2)), (height - ((int)(height * 0.1))*2));
		
		// Sets up back button
		backBtnSetup();
		
		// Creates a new leaderboard instance
		leaderboard = new Leaderboard();
		
		// Applies layout and applies necessary components
		bottomPnl.setLayout(new BorderLayout());
		bottomPnl.add(leaderboard, BorderLayout.CENTER);
		bottomPnl.add(backBtn, BorderLayout.SOUTH);
		
		// Setup for main leaderboardPanel
		this.setBackground(Color.LIGHT_GRAY);
        this.add(bottomPnl);
        this.setLayout(null);
	}

	/**
	 * Setup for back button
	 * Creates button, adds text, and actionListener
	 */
	public void backBtnSetup(){
		backBtn = new JButton();
		backBtn.setText("Back");
		backBtn.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String panelStr = panelSingleton.previousPanel();
		if(e.getSource() == backBtn) {
			this.remove(leaderboard);
			leaderboard = new Leaderboard();
			
			Container container = this.getParent();
			cardLayout.show(container, panelStr);
		}
		
	}
}
