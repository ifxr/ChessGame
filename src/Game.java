import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class PieceInfo{	
	String team;				// Represents what team the player will be in
	String piece;			// Represents the piece the player will play in '[*]' form
	
	public PieceInfo() {
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

public class Game extends JPanel{
	
	static PieceInfo[][] chessboard = new PieceInfo[8][8];
	static JButton[][] buttons;
	JPanel gamePanel;
	static JPanel scoreboardPanel;
	
	static GridBagConstraints gbc = new GridBagConstraints();
	static GridBagLayout gbl = new GridBagLayout();
	
	static JLabel currentTeamLabel = new JLabel();
	static JLabel eatenByWhiteLbl = new JLabel();
	static JLabel eatenByBlackLbl = new JLabel();
	static StringBuilder eatenByWhiteStr = new StringBuilder("");
	static StringBuilder eatenByBlackStr = new StringBuilder("");
	
	
	static int count;
	static int[] pieceCoords = {-1,-1};
	static int[] nextMove = {-1, -1};
	
	
	Game(){
		gamePanel = new JPanel();
		scoreboardPanel = new JPanel();
		
		gamePanel.setLayout(gbl);	 
	    gamePanel.setSize(450,450);
	    gamePanel.setLocation(100, 0);
	    gamePanel.setBackground(Color.WHITE);
	    
	    scoreboardPanel.setLayout(new BorderLayout());
	    scoreboardPanel.add(currentTeamLabel, BorderLayout.WEST);
	    
	    
	    this.setLayout(new BorderLayout());
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(scoreboardPanel, BorderLayout.SOUTH);
		
		String startingTeam = "WHITE";

		generateBoard(startingTeam); 
		updateBoard(startingTeam);	    
	 }
	
	private static class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int[] coords = new int[2];
    		coords = findButton(e.getSource());
    		playGame(coords);
		}
		
	}
	public static void enableGame(boolean flag) {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j<  buttons[0].length; j++) {
				buttons[i][j].setEnabled(flag);
			}
		}
	}
	
	public static void playGame(int[] coords) {
		String[] team = {"WHITE", "BLACK"};
		String teamSelected = team[count%2];
		
		if(teamSelected.equals(team[0])) {
			coords[0] = buttons.length-1 -coords[0];
			//coords[1] = buttons.length-1 -coords[1];
		}
		
		if(pieceCoords[0] == -1) {
			for(int i = 0; i < pieceCoords.length; i++)
				pieceCoords[i] = coords[i];
			potentialMove(pieceCoords, teamSelected);
		}
		else if (nextMove[0] == -1) {
			for(int i = 0; i < nextMove.length; i++)
				nextMove[i] = coords[i];
			
			int gameDecider = playMove(pieceCoords, nextMove);
			
			if (gameDecider == 1) {
				currentTeamLabel.setText("WINNER: "+ teamSelected);
				updateBoard(teamSelected);
				enableGame(false);
				return;
			}else if(gameDecider == 2) {
				updateBoard(teamSelected);
				return;
			}
			count++;	
		}
		teamSelected = team[count%2];
		currentTeamLabel.setText("Team: " + teamSelected);
		updateBoard(teamSelected);
		
	}
	
	private static int[] findButton(Object c) {
		int[] coords = new int[2];
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons[0].length; y++) {
                if (c.equals(buttons[x][y])) {
                	System.out.println("Coords- x: "+x+" y: "+y);
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
		String teamWhite = "WHITE";
		int errorCode = 0;
		
		// Coordinates for current selected piece
		int currentCol = currentPiece[1], currentRow = currentPiece[0];
		
		// Coordinates for possible move
		int possibleCol = possibleDestination[1], possibleRow = possibleDestination[0];
		
		//reset coords
		for(int i = 0; i<pieceCoords.length; i++) {
			pieceCoords[i] = -1;
			nextMove[i] = -1;
		}
		
		// checks to see if selected slot is one where the original piece can go to
		if(chessboard[possibleRow][possibleCol].getPiece().equals(possibleSlot)) {
			chessboard[possibleRow][possibleCol].setPiece(chessboard[currentRow][currentCol].getPiece());
			chessboard[possibleRow][possibleCol].setTeam(chessboard[currentRow][currentCol].getTeam());
			
			chessboard[currentRow][currentCol].setPiece(emptySlot);
			chessboard[currentRow][currentCol].setTeam(null);
		}
		// checks to see if the selected piece is indeed a piece that can be eaten
		else if(chessboard[possibleRow][possibleCol].getPiece().contains("{")) {
			
			// If 'King' gets eaten, game ends
			if(chessboard[possibleRow][possibleCol].getPiece().contains("K"))
				errorCode = 1;
			
			if(chessboard[possibleRow][possibleCol].getTeam().equals(teamWhite)) 
				eatenByBlackStr.append(chessboard[possibleRow][possibleCol].getPiece());
			else
				eatenByWhiteStr.append(chessboard[possibleRow][possibleCol].getPiece());
			
			chessboard[possibleRow][possibleCol].setPiece(chessboard[currentRow][currentCol].getPiece());
			chessboard[possibleRow][possibleCol].setTeam(chessboard[currentRow][currentCol].getTeam());
			
			chessboard[currentRow][currentCol].setPiece(emptySlot);
			chessboard[currentRow][currentCol].setTeam(null);
		}
		// return 2 means that the piece is not a valid slot
		else {
			errorCode =  2;
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
		
		if (errorCode == 1)
			return 1;
		else if (errorCode == 2) 
			return 2;
		else
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
			pawnMovement(gameRow, gameCol);
		}
		// "Rook" piece movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[R]")) {
			boolean[] out = new boolean[4]; 
			for(int i = 1; i < maxMovableSlots; i++) {
				out[0] = straightMovement(gameRow, gameCol, i, 0, out[0]);
				out[1] = straightMovement(gameRow, gameCol, -i, 0, out[1]);
				out[2] = straightMovement(gameRow, gameCol, 0, i, out[2]);
				out[3] = straightMovement(gameRow, gameCol, 0, -i, out[3]);
			}
			
		}
		// "Bishop" piece movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[B]")) {
			// lower right, upper left, lower left,
			boolean[] out = new boolean[4]; 
			for(int i = 1; i < maxMovableSlots; i++) {
				out[0] = diagonalMovement(gameRow, gameCol, i, i, out[0]);
				out[1] = diagonalMovement(gameRow, gameCol, -i, -i, out[1]);
				out[2] = diagonalMovement(gameRow, gameCol, i, -i, out[2]);
				out[3] = diagonalMovement(gameRow, gameCol, -i, i, out[3]);
			}
		}
		// 'Knight' Movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[k]")) {
			int[] directionOne = {2, 2, -2, -2, 1, 1, -1, -1};
			int[] directionTwo = {-1, 1, -1, 1, -2, 2, -2, 2};
			
			// 2Ux1L, 2Ux1R, 2Dx1L, 2Dx1R, 1Ux2L, 1Ux2R, 1Dx2L, 1Dx2R
			for(int i = 0; i<directionOne.length; i++)
				knightMovement(gameRow, gameCol, directionOne[i], directionTwo[i]);
		}
		// "Queen" piece movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[Q]")) {
			boolean[] out = new boolean[8]; 
			for(int i = 1; i < maxMovableSlots; i++) {
				out[0] = diagonalMovement(gameRow, gameCol, i, i, out[0]);
				out[1] = diagonalMovement(gameRow, gameCol, -i, -i, out[1]);
				out[2] = diagonalMovement(gameRow, gameCol, i, -i, out[2]);
				out[3] = diagonalMovement(gameRow, gameCol, -i, i, out[3]);
				out[4] = straightMovement(gameRow, gameCol, i, 0, out[4]);
				out[5] = straightMovement(gameRow, gameCol, -i, 0, out[5]);
				out[6] = straightMovement(gameRow, gameCol, 0, i, out[6]);
				out[7] = straightMovement(gameRow, gameCol, 0, -i, out[7]);
			}
		}
		// "King" piece movement
		else if(chessboard[gameRow][gameCol].getPiece().equals("[K]")) {
			boolean[] out = new boolean[8];
			for(int i = 1; i <= minMovableSlots; i++) {
				out[0] = diagonalMovement(gameRow, gameCol, i, i, out[0]);
				out[1] = diagonalMovement(gameRow, gameCol, -i, -i, out[1]);
				out[2] = diagonalMovement(gameRow, gameCol, i, -i, out[2]);
				out[3] = diagonalMovement(gameRow, gameCol, -i, i, out[3]);
				out[4] = straightMovement(gameRow, gameCol, i, 0, out[4]);
				out[5] = straightMovement(gameRow, gameCol, -i, 0, out[5]);
				out[6] = straightMovement(gameRow, gameCol, 0, i, out[6]);
				out[7] = straightMovement(gameRow, gameCol, 0, -i, out[7]);
			}
		}
		return 0;
	}
	
	/*
	 * Logic for Pawn movement
	 * Pawn movement allows it to move in 1 directions:
	 * It has the option to move 2 square the first move but after that it is 1 square per turn
	 */
	public static void pawnMovement(int gameRow, int gameCol) {
		String possibleMove = "[*]", emptySlot = "[ ]";
		String teamWhite = "WHITE";
		String currentTeam = chessboard[gameRow][gameCol].getTeam();
		int steps = 1;
		int direction = 1;
		
		if(gameRow == 6 || gameRow == 1)
			steps = 2;
		
		// Marks potential slots for pawns to move
		for(int i = 1; i <= steps; i++) {
			int temp = i;
			
			if (!currentTeam.equals(teamWhite))
				temp = -i;
			
			if(chessboard[gameRow + temp][gameCol].getPiece().equals(emptySlot)) {
				chessboard[gameRow + temp][gameCol].setPiece(possibleMove);
				buttons[gameRow + temp][gameCol].setText(possibleMove);
			}
		}
		
		// Checks diagonals to see if it can eat opposing pieces
		for(int i = -1; i <= 2; i=i+2) {
			int temp = i;
			//Checks got 
			if(currentTeam.equals(teamWhite)) {
				// Checks right Diagonal
				if(temp == 1) {
					if (gameRow + temp > chessboard.length - 1 || gameCol + temp > chessboard.length -1)
						continue;
					else 
						markTarget(gameRow+temp, gameCol+temp, currentTeam);
				}
				else if(temp == -1){
					// Checks left Diagonal
					if (gameRow - temp > chessboard.length - 1 || gameCol + temp <0)
						continue;
					else
						markTarget(gameRow-temp, gameCol+temp, currentTeam);
				}
			}
			else {
				if(temp == 1) {
					if (gameRow - temp < 0 || gameCol + temp > chessboard.length -1)
						continue;
					else
						markTarget(gameRow-temp, gameCol+temp, currentTeam);
				}
				else if(temp == -1) {
					// Checks left Diagonal
					if (gameRow + temp < 0 || gameCol + temp <0)
						continue;
					else
						markTarget(gameRow+temp, gameCol+temp, currentTeam);
				}
			}
		}
	}
	
	
	/*
	 * Main logic for 'Rook' movement and partial logic for the 'Queen' and 'King'
	 * This method allows selected piece to move in 4 directions:
	 * Upper Left, Upper Right, Lower Left, Lower Right
	 */
	public static boolean straightMovement(int gameRow, int gameCol, int directionOne, int directionTwo, boolean out) {
		String possibleMove = "[*]", emptySlot = "[ ]";
		String currentTeam = chessboard[gameRow][gameCol].getTeam();
		
		if(out == true)
			return true;
		
		// Validates that the potential slot stays inside of the board
		if(directionOne < 0) {
			if (gameRow + directionOne < 0)	
				return true;
		}else {
			if (gameRow + directionOne > chessboard.length - 1)	
				return true;
		}
		
		if(directionTwo < 0) {
			if (gameCol + directionTwo < 0)	
				return true;
		}else {
			if (gameCol + directionTwo > chessboard.length - 1)	
				return true;
		}
		// Up
		if(chessboard[gameRow + directionOne][gameCol + directionTwo].getPiece().equals(emptySlot)) {
			chessboard[gameRow+ directionOne][gameCol+ directionTwo].setPiece(possibleMove);
		}
		else {
			boolean temp = markTarget(gameRow+directionOne, gameCol + directionTwo, currentTeam);
			if(temp == true)
				return true;
		}
		return false;
		
	}
	
	/*
	 * Marks the piece that will get eaten by substituting the '[ ]' with '{ }'
	 */
	public static boolean markTarget(int gameRow, int gameCol, String playerTeam) {
		String targetTeam = chessboard[gameRow][gameCol].getTeam();

		if (targetTeam == null)
			return false;
		if (targetTeam.equals(playerTeam))
			return true;
		
		chessboard[gameRow][gameCol].setPiece(chessboard[gameRow][gameCol].getPiece().replace('[', '{').replace(']','}'));
		return true;
	}
	
	/*
	 * Main logic for 'Bishop' and partial logic for the 'Queen' and 'King'
	 * This method allows selected piece to move in 4 diagonal directions:
	 * Upper Left, Upper Right, Lower Left, Lower Right
	 */
	public static boolean diagonalMovement(int gameRow, int gameCol, int directionOne, int directionTwo, boolean out) {
		String possibleMove = "[*]", emptySlot = "[ ]";
		String currentTeam = chessboard[gameRow][gameCol].getTeam();
		
		if (out == true)
			return true;
		
		// Validates that the potential slot stays inside of the board
		if(directionOne < 0) {
			if (gameRow + directionOne < 0)	
				return true;
		}else {
			if (gameRow + directionOne > chessboard.length - 1)	
				return true;
		}
		
		if(directionTwo < 0) {
			if (gameCol + directionTwo < 0)	
				return true;
		}else {
			if (gameCol + directionTwo > chessboard.length - 1)	
				return true;
		}
		
		// Checks to see if the 'potentialMove' is an empty slot or if its taken
		if(chessboard[gameRow + directionOne][gameCol + directionTwo].getPiece().equals(emptySlot))
			chessboard[gameRow + directionOne][gameCol + directionTwo].setPiece(possibleMove);
		else{
			boolean temp = markTarget(gameRow + directionOne, gameCol + directionTwo, currentTeam);
			if(temp == true)	
				return true;
		}
		return false;
	}

	/*
	 * Main logic for 'Knight'
	 * This method allows selected piece to move to 8 different slots:
	 * 2Ux1L, 2Ux1R, 2Dx1L, 2Dx1R, 2Lx1U, 2Lx1D, 2Rx1U, 2Rx1D 
	 */
	public static void knightMovement(int gameRow, int gameCol, int directionOne, int directionTwo) {
		String possibleMove = "[*]", emptySlot = "[ ]";
		String currentTeam = chessboard[gameRow][gameCol].getTeam();
		
		// Validates that the potential slot stays inside of the board
		if(directionOne < 0) {
			if (gameRow + directionOne < 0)	
				return;
		}else {
			if (gameRow + directionOne > chessboard.length - 1)	
				return;
		}
		
		if(directionTwo < 0) {
			if (gameCol + directionTwo < 0)	
				return;
		}else {
			if (gameCol + directionTwo > chessboard.length - 1)	
				return;
		}
		// Checks to see if the 'potentialMove' is an empty slot or if its taken
		if(chessboard[gameRow + directionOne][gameCol + directionTwo].getPiece().equals(emptySlot)) 
			chessboard[gameRow + directionOne][gameCol + directionTwo].setPiece(possibleMove);
		else
			markTarget(gameRow+directionOne, gameCol+directionTwo, currentTeam);
	}
	
	/*
	 * Populates the chess board with the starting position of every piece
	 */
	public int generateBoard(String teamSelected) {
		String[] kingRow = {"[R]", "[k]", "[B]", "[Q]", "[K]", "[B]", "[k]", "[R]"};
		String[] pawnRow = {"[P]", "[P]", "[P]", "[P]", "[P]", "[P]", "[P]", "[P]"};
		String teamWhite = "WHITE", teamBlack = "BLACK";
		
		eatenByBlackStr.setLength(0);
		eatenByWhiteStr.setLength(0);
	    eatenByWhiteStr.append("Eaten: ");
	    eatenByBlackStr.append("Eaten: ");
	    currentTeamLabel.setText("Team: "+teamWhite);
	    
	    count = 0;
		
		buttons = new JButton[8][8];
		
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		
		// Generates an empty chess board
		for(int i = 0; i < chessboard.length; i++) {
			for(int j = 0; j < chessboard[0].length; j++) {
				chessboard[i][j] = new PieceInfo();
				chessboard[i][j].setPiece("[ ]");
				
				buttons[i][j] = new JButton();
		        buttons[i][j].addActionListener(new ButtonListener());
		        buttons[i][j].setText(chessboard[i][j].getPiece());
		        gbc.gridx = j; 
		        gbc.gridy = i;
		        gbc.gridheight = 1;
		        gbc.gridwidth = 1;
		        gbl.setConstraints(buttons[i][j], gbc);
		        gamePanel.add(buttons[i][j]); 
		        
		        buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
		        
			}
		}
		
		gbc.gridx = buttons.length; 
        gbc.gridy = buttons.length;
		
		// Populates chess board with all playable pieces
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
		return 0;
	}
	
	
	/*
	 * Updates the GUI board
	 */
	public static void updateBoard(String teamSelected) {
		String teamWhite = "WHITE", teamBlack = "BLACK";
		boolean whiteFlag = false;
		int bpn = 0, wpn = 0; // bpn = blackPrintNum, wpn = whitePrintNum
		if (teamSelected.equals(teamWhite)) {
			whiteFlag = true;
			scoreboardPanel.remove(eatenByBlackLbl);
			eatenByWhiteLbl.setText(eatenByWhiteStr.toString());
			scoreboardPanel.add(eatenByWhiteLbl, BorderLayout.EAST);
		}
		else {
			scoreboardPanel.remove(eatenByWhiteLbl);
			eatenByBlackLbl.setText(eatenByBlackStr.toString());
			scoreboardPanel.add(eatenByBlackLbl, BorderLayout.EAST);
		}
		
		// Based on the current turn, variables are filled to help orient the board
		for(int i = 0; i < chessboard.length; i++) {
			if(whiteFlag) { 
				bpn = chessboard.length - 1;
				wpn = 0; 
			}else {
				bpn = i;
				wpn = i;         
			}
			
			// Updates the text on each button
			for (int j = 0; j < chessboard[0].length; j++) {
				buttons[bpn - i + wpn][j].setText(chessboard[i][j].getPiece());
				if (chessboard[i][j].getPiece().contains("*")||chessboard[i][j].getPiece().contains("{")) 
					buttons[bpn - i + wpn][j].setForeground(Color.green);
				else if (chessboard[i][j].getTeam() == null)
					buttons[bpn - i + wpn][j].setForeground(Color.white);
				else if(chessboard[i][j].getTeam().equals(teamWhite))
					buttons[bpn - i + wpn][j].setForeground(Color.GRAY);
				else if(chessboard[i][j].getTeam().equals(teamBlack))
					buttons[bpn - i + wpn][j].setForeground(Color.BLACK);
			}
		}
		
	}
}
