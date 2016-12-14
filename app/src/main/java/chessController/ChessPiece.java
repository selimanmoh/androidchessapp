/*
 * Abstract class that represents each Chess Piece.
 * 
 * @author Mohamed Seliman and Mohammad Hadi Uppal
 * @version 1.0
 * @since 11-11-2016
 */

package chessController;

public abstract class ChessPiece {

	private int color;
	
	/*
	 * Constructor of ChessPiece that will take a color to set it to the field.
	 * @param color The color passed to the ChessPiece children
	 * @return nothing
	 */
	public ChessPiece(int color){
		this.color = color;
	}
	/*
	 * A getter method for the private color field.
	 * @param nothing
	 * @return int color
	 */
	public int getColor(){
		return this.color;
	}
	
	/*
	 * A method that checks whether or not a move from initialX, initialY to nextX,nextY is feasible 
	 * @param initialX Represents the initial rank
	 * @param initialY Represents the initial file
	 * @param nextX Represents the go to rank
	 * @param nextY Represents the go to file
	 * @return boolean that represents true or false if the move is possible.
	 */
	public abstract boolean makeMove(int initialX, int initialY, int nextX, int nextY);
}
