import java.awt.Color;

public class ColorSingleton {
	private static ColorSingleton instance;
	private Color selectedColor;
	
	private ColorSingleton() {
		
	}
	
	public static ColorSingleton getInstance() {
		if (instance == null) 
			instance = new ColorSingleton();

		return instance;
	}
	
	public Color getSelectedColor() {
		return selectedColor;
	}
	
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
}
