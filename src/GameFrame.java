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

public class GameFrame extends JFrame implements ActionListener{
	CardLayout cardLayout;
	JPanel container;
	
	GridBagLayout gbl = new GridBagLayout();
	JButton resetButton;
	
	Game game;
	MenuPanel menu;
	
	
	GameFrame(){
		this.setTitle("Chess");
		this.setSize(600,500); 
		this.setLayout(new BorderLayout()); 	 
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    resetButtonSetup();
	    
	    cardLayout = new CardLayout();
	    container = new JPanel(cardLayout);
	    
	    menu = new MenuPanel(cardLayout);
	    game = new Game();
	    
	    container.add(menu, "menu");
	    container.add(game, "game");
	    
	    cardLayout.show(container, "menu");
	    this.add(container, BorderLayout.CENTER);
	    
	    this.add(resetButton, BorderLayout.SOUTH);
	    //this.add(game, BorderLayout.CENTER);
	    //this.add(menu, BorderLayout.CENTER);
	    this.setVisible(true); 
	}

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
			//container.remove(game);
			game = new Game();
			container.add(game, "game");
			cardLayout.show(container, "game");
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
}
