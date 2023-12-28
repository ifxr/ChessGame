import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuContainer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The 'GameFrame' class generates the frame that will house all the panels.
 */
public class GameFrame extends JFrame implements ActionListener{
	CardLayout cardLayout;		// manager for panels
	JPanel container;			// panel used to store all other panels
	
	JButton resetButton;		// Resets game 
	
	Game game;					// instance of chess game
	MenuPanel menu;				// instance of menu panel
	
	
	/**
	 * When called upon, the constructor will generate a 600 by 500 frame. The panels that are created are managed by the 
	 * CardLayout class which allows us to transition between panels by keeping one visible at all times.
	 */
	GameFrame(){
		// Builds frame
		this.setTitle("Chess");
		this.setSize(600,500); 
		this.setLayout(new BorderLayout()); 	 
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    // Sets up reset button
	    resetButtonSetup();
	    
	    // Sets up CardLayout manager and accompanying container
	    cardLayout = new CardLayout();
	    container = new JPanel(cardLayout);
	    
	    // creates new instances of the Menu and Game Panel
	    menu = new MenuPanel(cardLayout);
	    game = new Game();
	    
	    // Adds the panels into the container
	    container.add(menu, "menu");
	    container.add(game, "game");
	    
	    // When game is ran, Menu panel will be first panel show on screen
	    cardLayout.show(container, "menu");
	    
	    // Adds and places the container and reset button into their place on the frame
	    this.add(container, BorderLayout.CENTER);
	    this.add(resetButton, BorderLayout.SOUTH);
	    this.setVisible(true); 
	}

	/**
	 * The 'resetButton' button is used to create a new instance of the game, thereby resetting the game to its default values.
	 * This function will create, sets location, and add an action listener to the 'resetButton' button
	 * @return nothing
	 */
	public void resetButtonSetup() {
		// TODO Auto-generated method stub
		resetButton = new JButton();
		resetButton.setText("Reset");
		resetButton.setSize(100, 50);
		resetButton.setLocation(0, 200);
		resetButton.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == resetButton) {
			container.remove(game);
			game = new Game();
			container.add(game, "game");
			cardLayout.show(container, "game");
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
}
