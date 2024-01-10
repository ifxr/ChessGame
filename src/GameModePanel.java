
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The 'MenuPanel' currently houses buttons which will allow you to select the type of game you want to generate. 
 * Whether that be against an AI, a local game, or an online game.
 */
public class GameModePanel extends JPanel implements ActionListener{
	
	GamePanel gamePnl;					// instance of chess game
	CardLayout cardLayout;		// manager for panels
	
	// Used to organize buttons within the main menu panel
	JPanel rowOnePanel;			
	JPanel rowTwoPanel;
	JPanel rowThreePanel;
	
	JButton pvpLocalBtn;		// Generate a local chess game vs another player
	JButton pvpOnlineBtn;		// Allows you to play online against another player
	JButton pveEasyBtn;			// Allows you to play against an AI, easy difficulty
	JButton pveMediumBtn;		// Allows you to play against an AI, medium difficulty
	JButton pveHardBtn;			// Allows you to play against an AI, hard difficulty
	
	/**
	 * 'MenuPanel' purpose is to create a panel and organize all the buttons that will be needed to access  certain features.
	 * 
	 * @param cardLayout: uses to change through the original panels created in the frame
	 */
	GameModePanel(CardLayout cardLayout){ 
		this.cardLayout = cardLayout;
		
		
		// Panels used to mainly for layout organization
		rowOnePanel = new JPanel();
		rowTwoPanel = new JPanel();
		rowThreePanel = new JPanel();
		
		// Setting each panel with a grid layout
		rowOnePanel.setLayout(new GridLayout(1, 3));
		rowTwoPanel.setLayout(new GridLayout(1, 3));
		rowThreePanel.setLayout(new GridLayout(1, 3));
		
		// Menu Panel dimensions
	    this.setSize(450,450);
	    this.setLocation(100, 0);
	    this.setBackground(Color.RED);
	    this.setLayout(new GridLayout(3, 1));
	    
	    // Adds panels to main panel
	    this.add(rowOnePanel);
	    this.add(rowTwoPanel);
	    this.add(rowThreePanel);
	    
	    // Generates all buttons
	    buttonSetup();
	}
	
	/**
	 * The 'buttonSetup' method will create multiple buttons, add action listeners, and add them to the main panel
	 */
	public void buttonSetup() {
		pvpLocalBtn = new JButton();
		pvpLocalBtn.setText("Local");
		pvpLocalBtn.addActionListener(this);
		rowOnePanel.add(pvpLocalBtn);
		
		pvpOnlineBtn = new JButton();
		pvpOnlineBtn.setText("Online");
		pvpOnlineBtn.addActionListener(this);
		rowTwoPanel.add(pvpOnlineBtn);
		
		pveEasyBtn = new JButton();
		pveEasyBtn.setText("Easy");
		pveEasyBtn.addActionListener(this);
		rowThreePanel.add(pveEasyBtn);
		
		pveMediumBtn = new JButton();
		pveMediumBtn.setText("Medium");
		pveMediumBtn.addActionListener(this);
		rowThreePanel.add(pveMediumBtn);
		
		pveHardBtn = new JButton();
		pveHardBtn.setText("Hard");
		pveHardBtn.addActionListener(this);
		rowThreePanel.add(pveHardBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Source: "+ e.getSource());
		if(e.getSource() == pvpLocalBtn) {
			Container container = this.getParent();
			gamePnl = new GamePanel(cardLayout);
			container.add(gamePnl, "gamePnl");
			cardLayout.show(container, "gamePnl");

			SwingUtilities.updateComponentTreeUI(this);
		}
		
		
	}
}
