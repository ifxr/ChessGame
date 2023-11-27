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
		String[] team = {"WHITE", "BLACK"};
		int teamCounter = 0;
		
		teamSelected = team[teamCounter];
		generateBoard(teamSelected);		//Generates Board
		
		// Game loop. Will iterate per turn until game is over
		while(true) {
			
			teamSelected = team[teamCounter%2];
			
			printBoard(teamSelected);
			System.out.println("Team: " + teamSelected +"\nWhat piece would you like to move?");
			String selectedPiece = myObj.nextLine().toUpperCase();
			
			Boolean temp = validateMove(selectedPiece);
			if (temp == false)
				break;
			
		    potentialMove(selectedPiece);
			printBoard(teamSelected);
			
			// Plays move
			System.out.println("Team: " + teamSelected +"\nWhere would you like to move your piece too?");
			String selectedSlot = myObj.nextLine().toUpperCase();
			
			temp = validateMove(selectedSlot);
			if (temp == false)
				break;
			
			int winner = playMove(selectedPiece, selectedSlot);
			if (winner == 1)
				break;
			
			teamCounter++;	
		}
		System.out.println(teamSelected+" Wins!!!");
	}
	
	/*
	 * Will move the piece to a valid position
	 */
	public static int playMove(String currentPiece, String possibleDestination) {
		String emptySlot = "[ ]";
		String possibleSlot = "[*]";
		
		// Coordinates for current selected piece
		int currentCol = currentPiece.charAt(0) - 65;
		int currentRow = currentPiece.charAt(1) - 49;
		
		// Coordinates for possible move
		int possibleCol = possibleDestination.charAt(0) - 65;
		int possibleRow = possibleDestination.charAt(1) - 49;
		
		if(chessboard[possibleRow][possibleCol].getPiece().equals(possibleSlot)) {
			chessboard[possibleRow][possibleCol].setPiece(chessboard[currentRow][currentCol].getPiece());
			chessboard[possibleRow][possibleCol].setTeam(chessboard[currentRow][currentCol].getTeam());
			
			chessboard[currentRow][currentCol].setPiece(emptySlot);
			chessboard[currentRow][currentCol].setTeam(null);
		}
		else if(chessboard[possibleRow][possibleCol].getPiece().contains("{")) {
			// If 'King' gets eaten, game ends
			if(chessboard[possibleRow][possibleCol].getPiece().contains("K"))
				return 1;
			
			chessboard[possibleRow][possibleCol].setPiece(chessboard[currentRow][currentCol].getPiece());
			chessboard[possibleRow][possibleCol].setTeam(chessboard[currentRow][currentCol].getTeam());
			
			chessboard[currentRow][currentCol].setPiece(emptySlot);
			chessboard[currentRow][currentCol].setTeam(null);
		}
		// Removes the "Potential" moves from the board
		for(int i = 0; i < chessboard.length; i++) {
			for(int j = 0; j < chessboard[0].length; j++) {
				if(chessboard[i][j].getPiece().equals(possibleSlot))
					chessboard[i][j].setPiece(emptySlot);
				
				if (chessboard[i][j].getPiece().contains("{")) 
					chessboard[i][j].setPiece(chessboard[i][j].getPiece().replace('{', '[').replace('}', ']'));
			}
		}
		
		return 0;
		
	}
	
	/*
	 * Shows potential moves for the user selected piece
	 */
	
	public static void potentialMove(String userInput) {
		String possibleMove = "[*]";
		String emptySlot = "[ ]";
		int maxMovableSlots = chessboard.length;
		int minMovableSlots = 1;
		int pawnMovableSlots = 2;
		
		
		int gameCol = userInput.charAt(0) - 65;
		int gameRow = userInput.charAt(1) - 49;
		
		// "Pawn" piece movement
		if (chessboard[gameRow][gameCol].getPiece().equals("[P]")){
			pawnMovement(gameRow, gameCol, pawnMovableSlots);
		}
		// "Rook" piece movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[R]")) {
			rookMovement(gameRow, gameCol, maxMovableSlots);
			
		}
		// "Bishop" piece movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[B]")) {
			bishopMovement(gameRow, gameCol, maxMovableSlots);
		}
		// 'Knight' Movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[k]")) {
			knightMovement(gameRow, gameCol, maxMovableSlots);
		}
		// "Queen" piece movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[Q]")) {
			rookMovement(gameRow, gameCol, maxMovableSlots);
			bishopMovement(gameRow, gameCol, maxMovableSlots);
		}
		// "King" piece movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[K]")) {
			rookMovement(gameRow, gameCol, minMovableSlots);
			bishopMovement(gameRow, gameCol, minMovableSlots);
		}
	}
	
	/*
	 * Logic for Pawn movement
	 * Pawn movement allows it to move in 1 directions:
	 * It has the option to move 2 square the first move but after that it is 1 square per turn
	 */
	public static void pawnMovement(int gameRow, int gameCol, int maxMovableSlots) {
		String possibleMove = "[*]";
		String emptySlot = "[ ]";
		
		if (gameRow == 1) {
			for(int i = 1; i <= maxMovableSlots; i++) {
				if(chessboard[gameRow+ i][gameCol].getPiece().equals(emptySlot))
					chessboard[gameRow+ i][gameCol].setPiece(possibleMove);
			}
		}
		else {
			if(chessboard[gameRow+1][gameCol].getPiece().equals(emptySlot)) 
				chessboard[gameRow+1][gameCol].setPiece(possibleMove);
		}
	}
	
	
	/*
	 * Main logic for 'Rook' movement and partial logic for the 'Queen' and 'King'
	 * This method allows selected piece to move in 4 directions:
	 * Upper Left, Upper Right, Lower Left, Lower Right
	 */
	public static void rookMovement(int gameRow, int gameCol, int maxMovableSlots) {
		String possibleMove = "[*]";
		String emptySlot = "[ ]";
		int directionAmount = 4;
		String currentTeam = chessboard[gameRow][gameCol].getTeam();
		
		for(int i = 0; i < directionAmount; i++) {
			for(int j = 1; j<= maxMovableSlots; j++) {
				if(i == 0) {
					// Validates that the potential slot stays inside of the board
					if (gameRow + j > chessboard.length - 1)
						break;
					// Up
					if(chessboard[gameRow + j][gameCol].getPiece().equals(emptySlot))
						chessboard[gameRow+ j][gameCol].setPiece(possibleMove);
					else {
						markTarget(gameRow+j, gameCol, currentTeam);
						break;
					}
				}
				else if(i == 1) {
					// Validates that the potential slot stays inside of the board
					if (gameRow - j < 0)
						break;
					// Down
					if(chessboard[gameRow - j][gameCol].getPiece().equals(emptySlot))
						chessboard[gameRow- j][gameCol].setPiece(possibleMove);
					else{
						markTarget(gameRow-j, gameCol, currentTeam);
						break;
					}
				}
				else if(i == 2) {
					// Validates that the potential slot stays inside of the board
					if (gameCol  + j > chessboard.length -1)
						break;
					// Right
					if(chessboard[gameRow][gameCol + j].getPiece().equals(emptySlot))
						chessboard[gameRow][gameCol + j].setPiece(possibleMove);
					else{
						markTarget(gameRow, gameCol+j, currentTeam);
						break;
					}
				}
				else if(i == 3) {
					// Validates that the potential slot stays inside of the board
					if (gameCol - j < 0)	
						break;
					// Left
					if(chessboard[gameRow][gameCol - j].getPiece().equals(emptySlot))
						chessboard[gameRow][gameCol - j].setPiece(possibleMove);
					else{
						markTarget(gameRow, gameCol-j, currentTeam);
						break;
					}
				}
				
			}
		}
	}
	
	public static void markTarget(int gameRow, int gameCol, String playerTeam) {
		String targetTeam = chessboard[gameRow][gameCol].getTeam();
		
		if (targetTeam.equals(null) || targetTeam.equals(playerTeam))
			return;
		System.out.println("Players team: "+ playerTeam);
		System.out.println("Target team: "+ targetTeam);
		chessboard[gameRow][gameCol].setPiece(chessboard[gameRow][gameCol].getPiece().replace('[', '{').replace(']','}'));
		
	}
	
	/*
	 * Main logic for 'Bishop' and partial logic for the 'Queen' and 'King'
	 * This method allows selected piece to move in 4 diagonal directions:
	 * Upper Left, Upper Right, Lower Left, Lower Right
	 */
	public static void bishopMovement(int gameRow, int gameCol, int maxMovableSlots) {
		String possibleMove = "[*]";
		String emptySlot = "[ ]";
		int directionAmount = 4;
		String currentTeam = chessboard[gameRow][gameCol].getTeam();
		
		for(int i = 0; i < directionAmount; i++) {
			for(int j = 1; j<= maxMovableSlots; j++) {
				if(i == 0) {
					// Validates that the potential slot stays inside of the board. 
					if (gameRow + j > chessboard.length -1 || gameCol + j > chessboard.length - 1)
						break;
					// Diagonal upper right
					if(chessboard[gameRow + j][gameCol + j].getPiece().equals(emptySlot))
						chessboard[gameRow+ j][gameCol + j].setPiece(possibleMove);
					else{
						markTarget(gameRow+j, gameCol+j, currentTeam);
						break;
					}
				}
				else if(i == 1) {
					// Validates that the potential slot stays inside of the board. Diagonal upper right
					if (gameRow - j < 0 || gameCol - j < 0)
						break;
					// Diagonal lower left
					if(chessboard[gameRow - j][gameCol - j].getPiece().equals(emptySlot))
						chessboard[gameRow- j][gameCol - j].setPiece(possibleMove);
					else{
						markTarget(gameRow-j, gameCol-j, currentTeam);
						break;
					}
				}
				else if(i == 2) {
					// Validates that the potential slot stays inside of the board
					if (gameRow  + j > chessboard.length -1 || gameCol - j < 0)
						break;
					// Diagonal upper left
					if(chessboard[gameRow + j][gameCol - j].getPiece().equals(emptySlot))
						chessboard[gameRow + j][gameCol - j].setPiece(possibleMove);
					else{
						markTarget(gameRow+j, gameCol-j, currentTeam);
						break;
					}
				}
				else if(i == 3) {
					// Validates that the potential slot stays inside of the board
					if (gameRow - j < 0 || gameCol  + j > chessboard.length -1)	
						break;
					// Diagonal lower right
					if(chessboard[gameRow - j][gameCol + j].getPiece().equals(emptySlot))
						chessboard[gameRow - j][gameCol + j].setPiece(possibleMove);
					else{
						markTarget(gameRow-j, gameCol+j, currentTeam);
						break;
					}
				}
				
			}
		}
	}

	/*
	 * Main logic for 'Knight'
	 * This method allows selected piece to move to 8 different slots:
	 * 2Ux1L, 2Ux1R, 2Dx1L, 2Dx1R, 2Lx1U, 2Lx1D, 2Rx1U, 2Rx1D 
	 */
	public static void knightMovement(int gameRow, int gameCol, int maxMovableSlots) {
		String possibleMove = "[*]";
		String emptySlot = "[ ]";
		int directionOne = 1;
		int directionTwo = 2;
		String currentTeam = chessboard[gameRow][gameCol].getTeam();
		
		for (int i = 0; i < maxMovableSlots; i++) {
			if(i == 0) {
				// Validates that the potential slot stays inside of the board
				if (gameRow + directionTwo > chessboard.length -1 || gameCol  - directionOne < 0)	
					continue;
				// 2Ux1L
				if(chessboard[gameRow + directionTwo][gameCol - directionOne].getPiece().equals(emptySlot))
					chessboard[gameRow +directionTwo][gameCol - directionOne].setPiece(possibleMove);
				else{
					markTarget(gameRow+directionTwo, gameCol-directionOne, currentTeam);
					break;
				}
			}
			else if(i == 1) {
				// Validates that the potential slot stays inside of the board
				if (gameRow + directionTwo > chessboard.length -1 || gameCol  + directionOne > chessboard.length -1)	
					continue;
				// 2Ux1R
				if(chessboard[gameRow + directionTwo][gameCol + directionOne].getPiece().equals(emptySlot))
					chessboard[gameRow + directionTwo][gameCol + directionOne].setPiece(possibleMove);
				else{
					markTarget(gameRow+directionTwo, gameCol+directionOne, currentTeam);
					break;
				}
			}
			else if(i == 2) {
				// Validates that the potential slot stays inside of the board
				if (gameRow - directionTwo < 0 || gameCol  - directionOne < 0)	
					continue;
				// 2Dx1L
				if(chessboard[gameRow - directionTwo][gameCol - directionOne].getPiece().equals(emptySlot))
					chessboard[gameRow - directionTwo][gameCol - directionOne].setPiece(possibleMove);
				else{
					markTarget(gameRow-directionTwo, gameCol-directionOne, currentTeam);
					break;
				}
			}
			else if(i == 3) {
				// Validates that the potential slot stays inside of the board
				if (gameRow - directionTwo < 0 || gameCol  + directionOne > chessboard.length - 1)	
					continue;
				// 2Dx1R
				if(chessboard[gameRow - directionTwo][gameCol + directionOne].getPiece().equals(emptySlot))
					chessboard[gameRow - directionTwo][gameCol + directionOne].setPiece(possibleMove);
				else{
					markTarget(gameRow-directionTwo, gameCol+directionOne, currentTeam);
					break;
				}
			}
			else if(i == 4) {
				// Validates that the potential slot stays inside of the board
				if (gameRow + directionOne > chessboard.length - 1 || gameCol - directionTwo < 0)	
					continue;
				// 1Ux2L
				if(chessboard[gameRow + directionOne][gameCol - directionTwo].getPiece().equals(emptySlot))
					chessboard[gameRow + directionOne][gameCol - directionTwo].setPiece(possibleMove);
				else{
					markTarget(gameRow+directionOne, gameCol-directionTwo, currentTeam);
					break;
				}
			}
			else if(i == 5) {
				// Validates that the potential slot stays inside of the board
				if (gameRow + directionOne > chessboard.length - 1 || gameCol + directionTwo > chessboard.length - 1)	
					continue;
				// 1Ux2R
				if(chessboard[gameRow + directionOne][gameCol + directionTwo].getPiece().equals(emptySlot))
					chessboard[gameRow + directionOne][gameCol + directionTwo].setPiece(possibleMove);
				else{
					markTarget(gameRow+directionOne, gameCol+directionTwo, currentTeam);
					break;
				} 
			}
			else if(i == 6) {
				// Validates that the potential slot stays inside of the board
				if (gameRow - directionOne < 0 || gameCol - directionTwo < 0)	
					continue;
				// 1Dx2L
				if(chessboard[gameRow - directionOne][gameCol - directionTwo].getPiece().equals(emptySlot))
					chessboard[gameRow - directionOne][gameCol - directionTwo].setPiece(possibleMove);
				else{
					markTarget(gameRow-directionOne, gameCol-directionTwo, currentTeam);
					break;
				} 
			}
			else if(i == 7) {
				// Validates that the potential slot stays inside of the board
				if (gameRow - directionOne < 0 || gameCol + directionTwo > chessboard.length - 1)	
					continue;
				// 1Dx2R
				if(chessboard[gameRow - directionOne][gameCol + directionTwo].getPiece().equals(emptySlot))
					chessboard[gameRow - directionOne][gameCol + directionTwo].setPiece(possibleMove);
				else{
					markTarget(gameRow-directionOne, gameCol+directionTwo, currentTeam);
					break;
				}
			}
		}
	}
	
	/*
	 * Checks to see if the move the user selected is a valid move in the chess board
	 */
	public static boolean validateMove(String userInput) {
		if(userInput.length() != 2) {
			return false;
		}
		else if(userInput.charAt(0) < 'A' || userInput.charAt(0) > 'H' ) {
			System.out.println("invalid column");
		}
		else if(userInput.charAt(1) < '1' || userInput.charAt(1) > '8' ) {
			System.out.println("invalid row");
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
