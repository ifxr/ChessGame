import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

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
	static JButton[][] buttons;
	static JFrame frame;
	static JLabel output = new JLabel("Click a button");
	static int count = 0;
	static int[] pieceCoords = {-1,-1};
	static int[] nextMove = {-1, -1};
	
	public static void main(String[] args) {
		String[] team = {"WHITE", "BLACK"};
		
		// an array of JButtons
		frame =new JFrame("Button Example"); 
		buttons = new JButton[8][8];
		
		ActionListener buttonListener = new ActionListener() {
			
		    	@Override
		    	public void actionPerformed(ActionEvent e) {
		    		int[] coords = new int[2];
		    		coords = findButton(e.getSource());
		    		playGame(coords);
		    	}
		};
		
	    generateBoard(team[0], buttonListener); 
	    updateBoard(team[0]);
	    
	    
		frame.add(output); 	 
	    frame.setSize(400,400);  
	    frame.setLayout(new GridLayout(9, 8));  
	    frame.setBackground(Color.BLACK);
	    frame.setVisible(true); 
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void playGame(int[] coords) {
		int x = coords[0];
		int y = coords[1];
		String[] team = {"WHITE", "BLACK"};
		String teamSelected = team[count%2];
		
		System.out.println(teamSelected);
		
		if(pieceCoords[0] == -1) {
			for(int i = 0; i < pieceCoords.length; i++)
				pieceCoords[i] = coords[i];
			//System.out.println("Count: "+count+" Piece Coords:"+pieceCoords[0]+", "+pieceCoords[1]);
			potentialMove(pieceCoords, teamSelected);
		}
		else if (nextMove[0] == -1) {
			for(int i = 0; i < nextMove.length; i++)
				nextMove[i] = coords[i];
			count++;
			//System.out.println("Count: "+count+" Next Move: "+nextMove[0]+", "+nextMove[1]);
			playMove(pieceCoords, nextMove);
		}
		System.out.println("team: "+ teamSelected);
		updateBoard(teamSelected);
		
	}
	private static int[] findButton(Object c) {
		int[] coords = new int[2];
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons[0].length; y++) {
                if (c.equals(buttons[x][y])) {
                    output.setText(x + "," + y + " clicked");
                    coords[0] = x;
                    coords[1] = y;
                    return coords;
                }
            }
        }
        return coords;
    }
	
	/*
	 * Will move the piece to a valid position
	 */
	public static int playMove(int[] currentPiece, int[] possibleDestination) {
		String emptySlot = "[ ]";
		String possibleSlot = "[*]";
		
		// Coordinates for current selected piece
		int currentCol = currentPiece[1];
		int currentRow = currentPiece[0];
		
		// Coordinates for possible move
		int possibleCol = possibleDestination[1];
		int possibleRow = possibleDestination[0];
		
		//reset coords
		for(int i = 0; i<pieceCoords.length; i++) {
			pieceCoords[i] = -1;
			nextMove[i] = -1;
		}
		
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
				if(chessboard[i][j].getPiece().equals(possibleSlot)) {
					chessboard[i][j].setPiece(emptySlot);
				}
				
				if (chessboard[i][j].getPiece().contains("{")) {
					chessboard[i][j].setPiece(chessboard[i][j].getPiece().replace('{', '[').replace('}', ']'));
				}
			}
		}
		
		return 0;
		
	}
	
	/*
	 * Shows potential moves for the user selected piece
	 * A 'return 1' means that the user selected an invalid piece
	 */
	
	public static int potentialMove(int[] userInput, String currentTeam) {
		int maxMovableSlots = chessboard.length;
		int minMovableSlots = 1;
		int pawnMovableSlots = 2;
		
		int gameCol = userInput[1];
		int gameRow = userInput[0];
		
		// Checks to see if selected piece is an empty slot. If so, it returns for 
		if (chessboard[gameRow][gameCol].getTeam() == null)
			return 1;
		
		// Checks to see that the correct team selects their pieces rather than opponents piece
		if (!chessboard[gameRow][gameCol].getTeam().equals(currentTeam)) 
			return 1;
		
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
		return 0;
	}
	
	/*
	 * Logic for Pawn movement
	 * Pawn movement allows it to move in 1 directions:
	 * It has the option to move 2 square the first move but after that it is 1 square per turn
	 */
	public static void pawnMovement(int gameRow, int gameCol, int maxMovableSlots) {
		String possibleMove = "[*]";
		String emptySlot = "[ ]";
		String teamWhite = "WHITE";
		String currentTeam = chessboard[gameRow][gameCol].getTeam();
		int tempPosition = 0;
		
		if (currentTeam.equals(teamWhite)) { 
			if(gameRow == 1){
				for(int i = 1; i <= maxMovableSlots; i++) {
					if(chessboard[gameRow+ i][gameCol].getPiece().equals(emptySlot)) {
						chessboard[gameRow+ i][gameCol].setPiece(possibleMove);
					}
				}
			}
			else {
				if(chessboard[gameRow+1][gameCol].getPiece().equals(emptySlot)) {
					chessboard[gameRow+1][gameCol].setPiece(possibleMove);
				}
			}
			// Checks right Diagonal
			if (gameRow + 1 > chessboard.length - 1 || gameCol + 1 > chessboard.length -1)
				return;
			else 
				markTarget(gameRow+1, gameCol+1, currentTeam);
			// Checks left Diagonal
			if (gameRow + 1 > chessboard.length - 1 || gameCol - 1 <0)
				return;
			else
				markTarget(gameRow+ 1, gameCol-1, currentTeam);
			
		}
		else {
			if(gameRow == 6){
				for(int i = 1; i <= maxMovableSlots; i++) {
					if(chessboard[gameRow- i][gameCol].getPiece().equals(emptySlot)) {
						chessboard[gameRow- i][gameCol].setPiece(possibleMove);
						
					}
				}
			}
			else {
				if(chessboard[gameRow-1][gameCol].getPiece().equals(emptySlot)) {
					chessboard[gameRow-1][gameCol].setPiece(possibleMove);
				}
				
			}
			// Checks right Diagonal
			if (gameRow - 1 < 0 || gameCol + 1 > chessboard.length -1)
				return;
			else
				markTarget(gameRow-1, gameCol+1, currentTeam);
			// Checks left Diagonal
			if (gameRow - 1 < 0 || gameCol - 1 <0)
				return;
			else
				markTarget(gameRow- 1, gameCol-1, currentTeam);
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
					if(chessboard[gameRow + j][gameCol].getPiece().equals(emptySlot)) {
						chessboard[gameRow+ j][gameCol].setPiece(possibleMove);
					}
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
					if(chessboard[gameRow - j][gameCol].getPiece().equals(emptySlot)) {
						chessboard[gameRow- j][gameCol].setPiece(possibleMove);
						
					}
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
					if(chessboard[gameRow][gameCol + j].getPiece().equals(emptySlot)) {
						chessboard[gameRow][gameCol + j].setPiece(possibleMove);
						
					}
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
					if(chessboard[gameRow][gameCol - j].getPiece().equals(emptySlot)) {
						chessboard[gameRow][gameCol - j].setPiece(possibleMove);
					}
					else{
						markTarget(gameRow, gameCol-j, currentTeam);
						break;
					}
				}
				
			}
		}
	}
	
	/*
	 * Marks the piece that will get eaten by substituting the '[ ]' with '{ }'
	 */
	public static void markTarget(int gameRow, int gameCol, String playerTeam) {
		String targetTeam = chessboard[gameRow][gameCol].getTeam();
		
		if (targetTeam == null || targetTeam.equals(playerTeam))
			return;
		
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
					if(chessboard[gameRow + j][gameCol + j].getPiece().equals(emptySlot)) {
						chessboard[gameRow+ j][gameCol + j].setPiece(possibleMove);
					}
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
					if(chessboard[gameRow - j][gameCol - j].getPiece().equals(emptySlot)) {
						chessboard[gameRow- j][gameCol - j].setPiece(possibleMove);
					}
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
					if(chessboard[gameRow + j][gameCol - j].getPiece().equals(emptySlot)) {
						chessboard[gameRow + j][gameCol - j].setPiece(possibleMove);
					}
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
					if(chessboard[gameRow - j][gameCol + j].getPiece().equals(emptySlot)) {
						chessboard[gameRow - j][gameCol + j].setPiece(possibleMove);
					}
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
				if(chessboard[gameRow + directionTwo][gameCol - directionOne].getPiece().equals(emptySlot)) {
					chessboard[gameRow +directionTwo][gameCol - directionOne].setPiece(possibleMove);
				}
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
				if(chessboard[gameRow + directionTwo][gameCol + directionOne].getPiece().equals(emptySlot)) {
					chessboard[gameRow + directionTwo][gameCol + directionOne].setPiece(possibleMove);
				}
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
				if(chessboard[gameRow - directionTwo][gameCol - directionOne].getPiece().equals(emptySlot)) {
					chessboard[gameRow - directionTwo][gameCol - directionOne].setPiece(possibleMove);
				}
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
				if(chessboard[gameRow - directionTwo][gameCol + directionOne].getPiece().equals(emptySlot)) {
					chessboard[gameRow - directionTwo][gameCol + directionOne].setPiece(possibleMove);
				}
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
				if(chessboard[gameRow + directionOne][gameCol - directionTwo].getPiece().equals(emptySlot)) {
					chessboard[gameRow + directionOne][gameCol - directionTwo].setPiece(possibleMove);
				}
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
				if(chessboard[gameRow + directionOne][gameCol + directionTwo].getPiece().equals(emptySlot)) {
					chessboard[gameRow + directionOne][gameCol + directionTwo].setPiece(possibleMove);
				}
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
				if(chessboard[gameRow - directionOne][gameCol - directionTwo].getPiece().equals(emptySlot)) {
					chessboard[gameRow - directionOne][gameCol - directionTwo].setPiece(possibleMove);
				}
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
				if(chessboard[gameRow - directionOne][gameCol + directionTwo].getPiece().equals(emptySlot)) {
					chessboard[gameRow - directionOne][gameCol + directionTwo].setPiece(possibleMove);
				}
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
	public static int generateBoard(String teamSelected, ActionListener buttonListener) {
		String[] kingRow = {"[R]", "[k]", "[B]", "[Q]", "[K]", "[B]", "[k]", "[R]"};
		String[] pawnRow = {"[P]", "[P]", "[P]", "[P]", "[P]", "[P]", "[P]", "[P]"};
		String teamWhite = "WHITE";
		String teamBlack = "BLACK";
		
		// Generates an empty chess board
		for(int i = 0; i < chessboard.length; i++) {
			for(int j = 0; j < chessboard[0].length; j++) {
				chessboard[i][j] = new ChessInfo();
				chessboard[i][j].setPiece("[ ]");
				
				buttons[i][j] = new JButton("[ ]");
		        buttons[i][j].addActionListener(buttonListener);
		        buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
		        frame.add(buttons[i][j]); 
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
	public static void updateBoard(String teamSelected) {
		String teamWhite = "WHITE";
		String teamBlack = "BLACK";
		
		// Prints from "WHITE" POV
		if (teamSelected.equals(teamWhite)) {
			for(int i = chessboard.length -1; i >= 0; i--) {
				for (int j = 0; j < chessboard[0].length; j++) {
					buttons[i][j].setText(chessboard[i][j].getPiece());
					if (chessboard[i][j].getPiece().contains("*")) 
						buttons[i][j].setForeground(Color.green);
					else if (chessboard[i][j].getTeam() == null)
						buttons[i][j].setForeground(Color.white);
					else if(chessboard[i][j].getTeam().equals(teamWhite))
						buttons[i][j].setForeground(Color.LIGHT_GRAY);
					else if(chessboard[i][j].getTeam().equals(teamBlack))
						buttons[i][j].setForeground(Color.BLACK);
				}
			}	
		}
		// Prints from "BLACK" POV
		else {
			for(int i = 0; i < chessboard.length; i++) {
				for (int j = chessboard[0].length -1; j >=0; j--) {
					buttons[i][j].setText(chessboard[i][j].getPiece());
					if (chessboard[i][j].getPiece().contains("*")) 
						buttons[i][j].setForeground(Color.green);
					else if (chessboard[i][j].getTeam() == null)
						buttons[i][j].setForeground(Color.white);
					else if(chessboard[i][j].getTeam().equals(teamWhite))
						buttons[i][j].setForeground(Color.LIGHT_GRAY);
					else if(chessboard[i][j].getTeam().equals(teamBlack))
						buttons[i][j].setForeground(Color.BLACK);
				}
			}
		}
	}
}
