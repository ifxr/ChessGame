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

public class MenuPanel extends JPanel implements ActionListener{
	
	Game game;
	JFrame frame;
	CardLayout cardLayout;
	
	JPanel rowOnePanel;
	JPanel rowTwoPanel;
	JPanel rowThreePanel;
	
	JButton pvpLocalBtn;
	JButton pvpOnlineBtn;
	JButton pveEasyBtn;
	JButton pveMediumBtn;
	JButton pveHardBtn;
	
	MenuPanel(CardLayout cardLayout){ 
		this.cardLayout = cardLayout;
		
		rowOnePanel = new JPanel();
		rowTwoPanel = new JPanel();
		rowThreePanel = new JPanel();
		
		rowOnePanel.setLayout(new GridLayout(1, 3));
		rowTwoPanel.setLayout(new GridLayout(1, 3));
		rowThreePanel.setLayout(new GridLayout(1, 3));
		
	    this.setSize(450,450);
	    this.setLocation(100, 0);
	    this.setBackground(Color.RED);
	    
	    this.setLayout(new GridLayout(3, 1));
	    this.add(rowOnePanel);
	    this.add(rowTwoPanel);
	    this.add(rowThreePanel);
	    
	    buttonSetup();
	}
	
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
			cardLayout.show(container, "game");

			SwingUtilities.updateComponentTreeUI(this);
		}
		
	}
}
