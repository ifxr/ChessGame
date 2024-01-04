import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GamePanel extends JPanel implements ActionListener{
	CardLayout cardLayout;
	JPanel buttonLayoutPnl;		// used to organize and place buttons
	
	JButton resetButton;		// Resets game 
	JButton menuButton;			// Goes back to main menu
	JButton settingsButton;		// Takes you to the settings panel
	
	Game game;
	Container container;
	
	GamePanel(CardLayout cardLayout){
		this.cardLayout = cardLayout;
		
		buttonLayoutSetup();
		game = new Game(cardLayout);
		
		this.setLayout(new BorderLayout());
		this.add(game, BorderLayout.CENTER);
		
		this.add(buttonLayoutPnl, BorderLayout.SOUTH);
	}
	
	/**
	 * 
	 */
	public void buttonLayoutSetup() {
		resetButtonSetup();
		menuButtonSetup();
		settingsButtonSetup();
		    
		buttonLayoutPnl = new JPanel();
		buttonLayoutPnl.setLayout(new GridLayout(1, 3));
		buttonLayoutPnl.add(menuButton);
		buttonLayoutPnl.add(resetButton);
		buttonLayoutPnl.add(settingsButton);
	}
	/**
	 * The 'resetButton' button is used to create a new instance of the game, thereby resetting the game to its default values.
	 * This function will create, sets text, and add an action listener to the 'resetButton' button
	 * @return nothing
	 */
	public void resetButtonSetup() {
		// TODO Auto-generated method stub
		resetButton = new JButton();
		resetButton.setText("Reset");
		resetButton.addActionListener(this);
		
	}
	
	/**
	 * The 'menuButton' button is used to go back to the main menu while you're in game 
	 * This function will create, set text, and add an action listener to the 'menuButton' button
	 */
	public void menuButtonSetup() {
		menuButton = new JButton();
		menuButton.setText("Main Menu");
		menuButton.addActionListener(this);
		
	}
	
	/**
	 *  The 'settingsButton' button is used to go and access the settings menu for the game
	 * This function will create, set text, and add an action listener to the 'menuButton' button
	 */
	public void settingsButtonSetup() {
		settingsButton = new JButton();
		settingsButton.setText("Settings");
		settingsButton.addActionListener(this);
	}
	
	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		container = this.getParent();
		// TODO Auto-generated method stub
		if(e.getSource() == resetButton) {
			this.remove(game);
			game = new Game(cardLayout);
			this.add(game, BorderLayout.CENTER);
			SwingUtilities.updateComponentTreeUI(this);
		}
		else if(e.getSource() == menuButton) {
			this.remove(this); 
			cardLayout.show(container, "menuPnl");
			SwingUtilities.updateComponentTreeUI(this);
			
		}
		else if(e.getSource() == settingsButton) {
			cardLayout.show(container, "settingsPnl");
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
}
