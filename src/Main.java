/**
 * Description: This program is my attempt at replicating one of the world's most popular games, chess. Aside from featuring
 * the chess game itself, this program will allow you to choose the opponent of your choosing (PvE or PvP) as well as save stats
 * inside of a match history log.
 * The 'opponent of your choosing' feature will allow the user to generate a 2 player local game on your device, a 2 player online
 * game, or game against an AI (easy, medium, hard).
 * 
 * @author ifxr
 * @version 1.0
 */
public class Main {
	/**
	 * The purpose of this method is to call the 'GameFrame' class, which will create a frame that will house the 
	 * multiple panels that the program will generate, including the game panel itself.
	 * @param args
	 * @return nothing
	 */
	public static void main(String[] args) {
		new GameFrame();
	}
}
