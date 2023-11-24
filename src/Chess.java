import java.util.Scanner;

class ChessInfo{	
	String team;				// Represents what team the player will be in
	String piece;			// Represents the piece the player will play in '[*]' form
	
	public ChessInfo() {
		team = null;
		piece = null;
		
	}

	public void setTeam(String team) {
		this.team = team;
	}
	public String getTeam() {
		return team;
	}
	
	public void setPiece(String piece) {
		this.piece = piece;
	}
	
	public String getPiece() {
		return piece;
	}
}

public class Chess {
	static ChessInfo[][] chessboard = new ChessInfo[8][8];
	
	public static void main(String[] args) {
		Scanner myObj = new Scanner(System.in);
		String teamSelected;
		
		// Asks the user to select a valid team
		while(true) {
			System.out.println("Would you like to be White or Black?");
			teamSelected = myObj.nextLine();
			teamSelected = teamSelected.toUpperCase();
			int temp = generateBoard(teamSelected);
			
			if (temp == 0)
				break;
		}
		
		printBoard(teamSelected);
		
		// Game loop. Will iterate per turn until game is over
		while(true) {
			System.out.println("Team: " + teamSelected +"\nWhat piece would you like to move?");
			String userInput = myObj.nextLine();
			
			Boolean temp = validateMove(userInput);
			if (temp == false)
				break;
			
			printBoard(teamSelected);
		}
	}
	
	public static boolean validateMove(String userInput) {
		userInput = userInput.toUpperCase();
		if(userInput.length() != 2) {
			return false;
		}
		else if(userInput.charAt(0) < 'A' || userInput.charAt(0) > 'H' ) {
			System.out.println("invalid column");
		}
		else if(userInput.charAt(1) < '1' || userInput.charAt(1) > '8' ) {
			System.out.println("invalid row");
		}
		else {
			int gameCol = userInput.charAt(0) - 65;
			int gameRow = userInput.charAt(1) - 49;
			
			chessboard[gameRow][gameCol].setPiece("[*]");
			chessboard[gameRow][gameCol].getPiece();
		}
		
		
		return true;
	}
	
	/*
	 * Populates the chess board with the starting position of every piece
	 */
	public static int generateBoard(String teamSelected) {
		
		String[] kingRow = {"[R]", "[k]", "[B]", "[Q]", "[K]", "[B]", "[k]", "[R]"};
		String[] pawnRow = {"[P]", "[P]", "[P]", "[P]", "[P]", "[P]", "[P]", "[P]"};
		String teamWhite = "WHITE";
		String teamBlack = "BLACK";
		
		// Generates an empty chess board
		for(int i = 0; i < chessboard.length; i++) {
			for(int j = 0; j < chessboard[0].length; j++) {
				chessboard[i][j] = new ChessInfo();
				chessboard[i][j].setPiece("[ ]");
			}
		}
		
		// Checks to see if correct team was selected
		if (!teamSelected.equals(teamWhite) && !teamSelected.equals(teamBlack)) {
			System.out.println("WRONG");
			return 1;
		}
		// Populates chess board with all playable pieces
		else {
			
			for(int i = 0; i < chessboard.length; i++) {
				// Populate king row and assign proper team: "WHITE"
				chessboard[0][i].setPiece(kingRow[i]);
				chessboard[0][i].setTeam(teamWhite);
				// Populate pawn row and assign proper team: "WHITE"
				chessboard[1][i].setPiece(pawnRow[i]);
				chessboard[1][i].setTeam(teamWhite);
				// Populate king row and assign proper team: "BLACK"
				chessboard[chessboard.length-1][i].setPiece(kingRow[i]);
				chessboard[chessboard.length-1][i].setTeam(teamBlack);
				// Populate pawn row and assign proper team: "BLACK"
				chessboard[chessboard.length-2][i].setPiece(pawnRow[i]);
				chessboard[chessboard.length-2][i].setTeam(teamBlack);
			}
			
		}
		
		return 0;
	}
	
	
	/*
	 * Prints out the chess board to console
	 */
	public static void printBoard(String teamSelected) {
		String teamWhite = "WHITE";
		
		// Prints from "WHITE" POV
		if (teamSelected.equals(teamWhite)) {
			for(int i = chessboard.length -1; i >= 0; i--) {
				for (int j = 0; j < chessboard[0].length; j++) {
					System.out.print(chessboard[i][j].getPiece());
				}
				System.out.println();
			}	
		}
		// Prints from "BLACK" POV
		else {
			for(int i = 0; i < chessboard.length; i++) {
				for (int j = chessboard[0].length -1; j >=0; j--) {
					System.out.print(chessboard[i][j].getPiece());
				}
				System.out.println();
			}
		}
	}
}
