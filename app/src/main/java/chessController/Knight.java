/*
 *  @author Mohamed Seliman and Mohammad Hadi Uppal
 *  @version 1.0
 *  @since 11-11-2016
 *  
 *  The Knight class has no fields, but represents the Knight piece.
 */

package chessController;
import chess.Chess;

public class Knight extends ChessPiece {

	public Knight(int color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see chessController.ChessPiece#makeMove(int, int, int, int)
	 */
	@Override
	public boolean makeMove(int initialX, int initialY, int nextX, int nextY) {
		if(Chess.game.board[nextX][nextY].capacity != null && Chess.game.board[nextX][nextY].capacity.getColor() == this.getColor())
			return false;

			if((Math.abs(initialX-nextX) == 2 && Math.abs(initialY-nextY) == 1)) //goes down or up 2 squares and then left or right 1 square
				return true;
			else if(Math.abs(initialX-nextX) == 1 && Math.abs(initialY-nextY) == 2) //goes left or right 2 squares, then up or down 1 square
				return true;

		return false;
	}

}
