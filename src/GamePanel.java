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
	
	JButton resetBtn;		// Resets game 
	JButton menuBtn;			// Goes back to main menu
	JButton settingsBtn;		// Takes you to the settings panel
	JButton resignBtn;
	
	Game game;
	Container container;

	private PanelSingleton panelSingleton;
	
	GamePanel(CardLayout cardLayout){
		panelSingleton = PanelSingleton.getInstance();
		
		this.cardLayout = cardLayout;
		
		buttonLayoutSetup();
		game = new Game();
		
		this.setLayout(new BorderLayout());
		this.add(game, BorderLayout.CENTER);
		
		this.add(buttonLayoutPnl, BorderLayout.SOUTH);
	}
	
	/**
	 * 
	 */
	public void buttonLayoutSetup() {
		resignButtonSetup();
		resetButtonSetup();
		menuButtonSetup();
		settingsButtonSetup();
		    
		buttonLayoutPnl = new JPanel();
		buttonLayoutPnl.setLayout(new GridLayout(1, 4));
		buttonLayoutPnl.add(menuBtn);
		buttonLayoutPnl.add(settingsBtn);
		buttonLayoutPnl.add(resetBtn);
		buttonLayoutPnl.add(resignBtn);
	}
	
	/**
	 *  The 'resignBtn' button is used to concede defeat in the middle of a game before checkmate
	 * This function will create, set text, and add an action listener to the 'resignBtn' button
	 */
	public void resignButtonSetup() {
		resignBtn = new JButton();
		resignBtn.setText("Resign");
		resignBtn.addActionListener(this);
	}
	
	/**
	 * The 'resetBtn' button is used to create a new instance of the game, thereby resetting the game to its default values.
	 * This function will create, sets text, and add an action listener to the 'resetBtn' button
	 * @return nothing
	 */
	public void resetButtonSetup() {
		// TODO Auto-generated method stub
		resetBtn = new JButton();
		resetBtn.setText("Reset");
		resetBtn.addActionListener(this);
		
	}
	
	/**
	 * The 'menuBtn' button is used to go back to the main menu while you're in game 
	 * This function will create, set text, and add an action listener to the 'menuBtn' button
	 */
	public void menuButtonSetup() {
		menuBtn = new JButton();
		menuBtn.setText("Main Menu");
		menuBtn.addActionListener(this);
		
	}
	
	/**
	 *  The 'settingsBtn' button is used to go and access the settings menu for the game
	 * This function will create, set text, and add an action listener to the 'menuBtn' button
	 */
	public void settingsButtonSetup() {
		settingsBtn = new JButton();
		settingsBtn.setText("Settings");
		settingsBtn.addActionListener(this);
	}
	
	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		container = this.getParent();
		panelSingleton.currentPanel("gamePnl");
		// TODO Auto-generated method stub
		if(e.getSource() == resetBtn) {
			this.remove(game);
			game = new Game();
			this.add(game, BorderLayout.CENTER);
			SwingUtilities.updateComponentTreeUI(this);
		}
		else if(e.getSource() == menuBtn) {
			this.remove(this); 
			cardLayout.show(container, "menuPnl");
			SwingUtilities.updateComponentTreeUI(this);
			
		}
		else if(e.getSource() == settingsBtn) {
			cardLayout.show(container, "settingsPnl");
			SwingUtilities.updateComponentTreeUI(this);
		}
		else if(e.getSource() == resignBtn) {
			Game.boolResign = true;
			Game.enableGame(false);
			
		}
	}
}
