import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuContainer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The 'GameFrame' class generates the frame that will house all the panels.
 */
public class GameFrame extends JFrame{
	CardLayout cardLayout;		// manager for panels
	JPanel container;			// panel used to store all other panels
	
	JPanel buttonLayoutPnl;		// used to organize and place buttons
	
	JButton resetButton;		// Resets game 
	JButton menuButton;			// Goes back to main menu
	JButton settingsButton;		// Takes you to the settings panel
	
	//Game game;					// instance of chess game
	//GamePanel gamePnl;
	MenuPanel menuPnl;				// instance of menu panel
	SettingsPanel settingsPnl;		// instance of settings panel
	
	
	/**
	 * When called upon, the constructor will generate a 600 by 500 frame. The panels that are created are managed by the 
	 * CardLayout class which allows us to transition between panels by keeping one visible at all times.
	 */
	GameFrame(){
		// Builds frame
		this.setTitle("Chess");
		this.setSize(1000,700); 
		this.setResizable(false);
		this.setLayout(new BorderLayout()); 	 
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    // Sets up CardLayout manager and accompanying container
	    cardLayout = new CardLayout();
	    container = new JPanel(cardLayout);
	    
	    // creates new instances of the Menu and Game Panel
	    menuPnl = new MenuPanel(cardLayout);
	    //game = new Game();
	    //gamePnl = new GamePanel(cardLayout);
	    settingsPnl = new SettingsPanel(cardLayout);
	   
	    
	    // Adds the panels into the container
	    container.add(menuPnl, "menuPnl");
	    //container.add(gamePnl, "gamePnl");
	    container.add(settingsPnl, "settingsPnl");
	    
	    // When game is ran, Menu panel will be first panel show on screen
	    cardLayout.show(container, "menuPnl");
	    
	    // Adds and places the container and reset button into their place on the frame
	    this.add(container, BorderLayout.CENTER);
	    this.setVisible(true); 
	}
}
