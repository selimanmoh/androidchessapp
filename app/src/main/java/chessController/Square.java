/*
 * @author Mohamed Seliman and Muhammad Hadi Uppal
 * @version 1.0
 * @since 11-11-2016
 * 
 * The Square class represents the game board squares that hold Chess Pieces. 
 */

package chessController;

public class Square {

	public ChessPiece capacity;
	/*
	 * Two different constructors for an empty square and a filled square
	 */
	public Square(){
		this.capacity = null;
	}
	public Square(ChessPiece piece){  //when specified, this constructor is for pieces on squares
		this.capacity = piece;
	}
}
