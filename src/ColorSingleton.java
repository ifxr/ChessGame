import java.awt.Color;

public class ColorSingleton {
	private static ColorSingleton instance;
	private Color selectedColorOne;
	private Color selectedColorTwo;
	
	private ColorSingleton() {
		
	}
	
	public static ColorSingleton getInstance() {
		if (instance == null) 
			instance = new ColorSingleton();

		return instance;
	}
	
	public Color getSelectedColorOne() {
		if(selectedColorOne == null) 
			selectedColorOne = Color.GRAY;
		return selectedColorOne;
	}
	
	public Color getSelectedColorTwo() {
		if(selectedColorTwo == null) 
			selectedColorTwo = Color.WHITE;
		
		return selectedColorTwo;
	}
	
	public void setSelectedColorOne(Color selectedColor) {
		this.selectedColorOne = selectedColor;
	}
	
	public void setSelectedColorTwo(Color selectedColor) {
		this.selectedColorTwo = selectedColor;
	}
	
}
