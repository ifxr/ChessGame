import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SettingsPanel extends JPanel implements ActionListener {
	private PanelSingleton panelSingleton;
	private ColorSingleton colorSingleton;
	
	JButton colorOneBtn;
	JButton colorTwoBtn;
	JButton backBtn;
	
	static Color colorOne;
	static Color colorTwo;
	
	CardLayout cardLayout;
	
	SettingsPanel(CardLayout cardLayout){ 
		this.cardLayout = cardLayout;
		
		colorSingleton = ColorSingleton.getInstance();
		panelSingleton = PanelSingleton.getInstance();
		
		JPanel panel = new JPanel();
		JLabel titleLabel = new JLabel("SETTINGS");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//panel.setBackground(Color.red);
		this.addComponentListener(new ComponentAdapter() {
    	    public void componentResized(ComponentEvent e) {
    	    	int width = getWidth();
    			int height = getHeight();
    	    	panel.setBounds((int)(width * 0.2), (int)(height * 0.2)-20, 
    	    			(width - ((int)(width * 0.2)*2)), (height - ((int)(height * 0.2))*2));
    	    }
    	});
		
		buttonSetup();
        
		panel.setLayout(new GridLayout(4, 1));
		panel.add(titleLabel);
        panel.add(colorOneBtn);
        panel.add(colorTwoBtn);
        panel.add(backBtn);
        
        this.setBackground(Color.LIGHT_GRAY);
        this.add(panel);
        this.setLayout(null);
	}
	
	public void buttonSetup() {
		colorOneBtn = new JButton("Choose Color One");
        colorOneBtn.addActionListener(this);
        
        colorTwoBtn = new JButton("Choose Color Two");
        colorTwoBtn.addActionListener(this);
        
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String panelStr = panelSingleton.previousPanel();
		if (e.getSource() == colorOneBtn) {
			colorOne = JColorChooser.showDialog(this, "Choose a color", null);
			colorSingleton.setSelectedColorOne(colorOne);
		}
		else if (e.getSource() == colorTwoBtn) {
			colorTwo = JColorChooser.showDialog(this, "Choose a color", null);
			colorSingleton.setSelectedColorTwo(colorTwo);
			
		}else if(e.getSource() == backBtn) {
			Game.updateBoard("");
			Container container = this.getParent();
			cardLayout.show(container, panelStr);
			
		}
    }
}
