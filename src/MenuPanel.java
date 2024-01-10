
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The 'MenuPanel' currently houses buttons which will allow you to select the type of game you want to generate. 
 * Whether that be against an AI, a local game, or an online game.
 */
public class MenuPanel extends JPanel implements ActionListener{
	private PanelSingleton panelSingleton;
	
	GamePanel gamePnl;					// instance of chess game
	CardLayout cardLayout;		// manager for panels
	JFrame frame;
	
	JPanel panel;
	JLabel titleLbl;
	
	JButton playBtn;
	JButton settingsBtn;
	
	
	MenuPanel(CardLayout cardLayout, JFrame frame){
		panelSingleton = PanelSingleton.getInstance();
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		
		
		this.frame = frame;
		this.cardLayout = cardLayout;
		
		int width = frame.getWidth();
		int height = frame.getHeight();
		System.out.println("Height: "+ height+" width: "+width);
		panel.setBounds((int)(width * 0.2), (int)(height * 0.2)-20, 
    			(width - ((int)(width * 0.2)*2)), (height - ((int)(height * 0.2))*2));
		
		menuComponentSetup();
		
		this.setBackground(Color.LIGHT_GRAY);
        this.add(panel);
        this.setLayout(null);
	}
	/**
	 * 'MenuPanel' purpose is to create a panel and organize all the buttons that will be needed to access  certain features.
	 * 
	 * @param cardLayout: uses to change through the original panels created in the frame
	 */
	MenuPanel(CardLayout cardLayout){ 
		this.cardLayout = cardLayout;
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		
		this.addComponentListener(new ComponentAdapter() {
    	    public void componentResized(ComponentEvent e) {
    	    	int width = getWidth();
    			int height = getHeight();
    			System.out.println("Height: "+ height+" width: "+width);
    			
    	    	panel.setBounds((int)(width * 0.2), (int)(height * 0.2)-20, 
    	    			(width - ((int)(width * 0.2)*2)), (height - ((int)(height * 0.2))*2));
    	    }
    	});
		
		menuComponentSetup();
		
		this.setBackground(Color.LIGHT_GRAY);
        this.add(panel);
        this.setLayout(null);
	}
	
	/**
	 * The 'buttonSetup' method will create multiple buttons, add action listeners, and add them to the main panel
	 */
	public void menuComponentSetup() {
		titleLbl = new JLabel("CHESS");
		titleLbl.setHorizontalAlignment(JLabel.CENTER);
		panel.add(titleLbl);
		
		playBtn = new JButton("Play");
		playBtn.addActionListener(this);
		panel.add(playBtn);
		
		settingsBtn = new JButton("Settings");
		settingsBtn.addActionListener(this);
		panel.add(settingsBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Container container = this.getParent();
		panelSingleton.currentPanel("menuPnl");
		
		System.out.println("TEST 1: "+ cardLayout.toString());
		System.out.println("TEST 2: "+ panelSingleton.previousPanel());
		
		if(e.getSource() == playBtn) {
			gamePnl = new GamePanel(cardLayout);
			container.add(gamePnl, "gamePnl");
			cardLayout.show(container, "gamePnl");

			SwingUtilities.updateComponentTreeUI(this);
		}else if(e.getSource() == settingsBtn) {
			cardLayout.show(container, "settingsPnl");
			SwingUtilities.updateComponentTreeUI(this);
		}
		
		
	}
}
