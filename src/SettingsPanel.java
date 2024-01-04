import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel implements ActionListener {
	private ColorSingleton colorSingleton;
	
	JButton colorOneBtn;
	JButton colorTwoBtn;
	JButton backBtn;
	JButton testBtn;
	
	static Color colorOne;
	static Color colorTwo;
	
	CardLayout cardLayout;
	
	SettingsPanel(CardLayout cardLayout){ 
		this.cardLayout = cardLayout;
		colorSingleton = ColorSingleton.getInstance();
		
		buttonSetup();
        
        this.add(colorOneBtn);
        this.add(colorTwoBtn);
        this.add(backBtn);
        this.add(testBtn);
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
	
	public void setColorOne() {
		if(colorOne == null)
			colorOne = Color.gray;
		else
			colorOne = JColorChooser.showDialog(this, "Choose a color", null);
		
		colorSingleton.setSelectedColor(colorOne);
	}

	public Color getColorTwo() {
		if (colorTwo == null)
			colorTwo = Color.white;
		return colorTwo;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == colorOneBtn) {
			setColorOne();
		}
		else if (e.getSource() == colorTwoBtn) {
			colorTwo = JColorChooser.showDialog(this, "Choose a color", null);
			
		}else if(e.getSource() == backBtn) {
			Container container = this.getParent();
			cardLayout.show(container, "gamePnl");
		}
		else if(e.getSource() == testBtn) {
			System.out.println("Color: "+ colorOne);
		}
    }
}
