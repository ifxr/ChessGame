import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * 'PieceInfo' class is the heart and soul of game. The game is essentially a 2D array of 8 slots each filled with 
 * this class as objects. Each object will hold two variables: a string called 'team' which will hold the team the piece
 * is apart of and a string called 'piece' which will hold the actual piece (i.e. King, Queen, Rook, Bishop, Knight, Pawn).
 */
public class PieceInfo{	
	String team;				// Represents what team the playable piece will be in
	String piece;				// Represents the piece the player will play in '[*]' form
	ImageIcon image;				// Will hold the image representing their corresponding piece
	int moveCount;				// Counter for the amount of times the specified pieces moves
	
	public PieceInfo() {
		team = null;
		piece = null;
		image = null;
		moveCount = 0;
		
	}
	
	/**
	 * Setter method to set the team
	 * @param team
	 */
	public void setTeam(String team) {
		this.team = team;
	}
	
	/**
	 * Getter method to get the team
	 * @return
	 */
	public String getTeam() {
		return team;
	}
	
	/**
	 * Setter method to set the respective piece
	 * @param piece
	 */
	public void setPiece(String piece) {
		this.piece = piece;
	}
	
	/**
	 * Getter method to get the respective piece
	 * @return
	 */
	public String getPiece() {
		return piece;
	}
	
	/**
	 * Setter method to set an image to piece
	 * @return
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	/**
	 * Getter method to get the image representing the specific piece
	 * @return
	 */
	public ImageIcon getImage() {
		return image;
	}
	
	/**
	 * Setter method used to set move counter
	 * @return
	 */
	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
	
	/**
	 * method that increments the counter everytime it is called
	 * @return
	 */
	public void incrementCount() {
		moveCount++;
	}
	
	/**
	 * Getter method used to retrieve counter
	 * @return
	 */
	public int getMoveCount() {
		return moveCount;
	}
	
}