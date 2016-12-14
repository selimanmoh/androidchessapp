/*
 *  @author Mohamed Seliman and Mohammad Hadi Uppal
 *  @version 1.0
 *  @since 11-11-2016
 *  
 *  The Rook class represents the rook chess piece. It has a start boolean used in conjunction with the King's start
 *  to implement the castling functionality in Chess.
 */

package chessController;
import chess.Chess;

public class Rook extends ChessPiece {

	public boolean start = true;
	
	public Rook(int color) {
		super(color);
		// TODO Auto-generated constructor stub
	}
/*
 * (non-Javadoc)
 * @see chessController.ChessPiece#makeMove(int, int, int, int)
 */
	@Override
	public boolean makeMove(int initialX, int initialY, int nextX, int nextY) { // 0,0 -- 2,0
		// TODO Auto-generated method stub
		if(Chess.game.board[nextX][nextY].capacity != null && Chess.game.board[nextX][nextY].capacity.getColor() == this.getColor())
			return false; //same side piece if capacity is occupied

		if(Math.abs(nextX - initialX) >= 0 && nextY-initialY == 0){ //only going straight up or down

			int i;

			if(nextX > initialX){ //going up
				i = initialX + 1;
				while(i!=nextX){
					if(Chess.game.board[i][nextY].capacity != null)
						return false; 
					i++;
				}
			}
			else if(nextX < initialX){ //going down
				i = initialX - 1;
				while(i!=nextX){
					if(Chess.game.board[i][nextY].capacity!=null)
						return false;
					i--;
				}
			}
			return true;
		}
		else if(Math.abs(nextY-initialY) >= 0 && nextX-initialX == 0){ //going right or left

			int j; 

			if(nextY>initialY){ //going right
				 j = initialY + 1;
				while(j!=nextY){
					if(Chess.game.board[nextX][j].capacity!=null)
						return false;

					j++;
				}
			}
			else if(nextY < initialY){ //going left
				 j = initialY - 1;
				while(j!=nextY){
					if(Chess.game.board[nextX][j].capacity!=null)
						return false;

					j--;
				}
			}
			return true;
		}
		
		return false;
			
	}

}
