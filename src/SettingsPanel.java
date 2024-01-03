import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel implements ActionListener {
	private static SettingsPanel instance;
	
	JButton colorOneBtn;
	JButton colorTwoBtn;
	JButton backBtn;
	JButton testBtn;
	
	Color colorOne;
	Color colorTwo;
	
	static CardLayout cardLayout;
	
	SettingsPanel(CardLayout cardLayout){ 
		this.cardLayout = cardLayout;
		
		buttonSetup();
        
        this.add(colorOneBtn);
        this.add(colorTwoBtn);
        this.add(backBtn);
        this.add(testBtn);
	}
	
	public static SettingsPanel getInstance(CardLayout cardLayout) {
		if (instance == null) {
            instance = new SettingsPanel(cardLayout);
        }
		return instance;
	}
	
	public void buttonSetup() {
		colorOneBtn = new JButton("Choose Color One");
        colorOneBtn.addActionListener(this);
        
        colorTwoBtn = new JButton("Choose Color Two");
        colorTwoBtn.addActionListener(this);
        
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        
        testBtn = new JButton("test");
        testBtn.addActionListener(this);
	}
	
	public Color getColorOne() {
		System.out.println("Color One: "+ colorOne);
		if(colorOne == null)
			colorOne = Color.gray;
		return colorOne;
	}

	public Color getColorTwo() {
		if (colorTwo == null)
			colorTwo = Color.white;
		return colorTwo;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == colorOneBtn) {
			colorOne = JColorChooser.showDialog(this, "Choose a color", null);
		}
		else if (e.getSource() == colorTwoBtn) {
			colorTwo = JColorChooser.showDialog(this, "Choose a color", null);
			
		}else if(e.getSource() == backBtn) {
			Container container = this.getParent();
			cardLayout.show(container, "gamePnl");
		}
		else if(e.getSource() == testBtn) {
			System.out.println("Color: "+ getColorOne());
		}
    }
}
