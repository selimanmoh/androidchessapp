/*
 * 
 * @author Mohamed Seliman and Mohammad Hadi Uppal
 * @version 1.0
 * @since 11-11-2016
 */

package chessController;

import chess.Chess;

public class Bishop extends ChessPiece {

	/*
	 * The constructor uses the parent abstract class to set the color field.
	 * @param color The color of the Bishop created.
	 * @return nothing as it's a constructor.
	 */
	public Bishop(int color) {
		super(color);
	}
	/*
	 * (non-Javadoc)
	 * @see chessController.ChessPiece#makeMove(int, int, int, int)
	 */

	@Override
	public boolean makeMove(int initialX, int initialY, int nextX, int nextY) { //1,2 to 1,1
		
		if(Chess.game.board[nextX][nextY].capacity!= null && Chess.game.board[nextX][nextY].capacity.getColor() == this.getColor())
			return false;

		if(Math.abs(nextY - initialY) == Math.abs(nextX-initialX)){
			if(nextX>initialX && nextY>initialY){
				int i = initialX + 1;
				int j = initialY + 1;
				
				while(i!=nextX && j!=nextY){
					if(Chess.game.board[i][j].capacity!= null)
						return false;
					i++;
					j++;
				}
			}
			else if(nextX>initialX && nextY<initialY){
				int i = initialX + 1;
				int j = initialY - 1;
				while(i!=nextX && j!=nextY){
					if(Chess.game.board[i][j].capacity!= null)
						return false;
					i++;
					j--;
				}
			}
			else if(nextX<initialX && nextY>initialY){
				int i = initialX - 1; //7 -> 3
				int j = initialY + 1; //3 -> 7
				while(i!=nextX && j!=nextY){
					if(Chess.game.board[i][j].capacity!= null)
						return false;
					i--;
					j++;
				}
			}
			else if(nextX<initialX && nextY<initialY){
				int i = initialX - 1;
				int j = initialY - 1;
				while(i!=nextX && j!=nextY){
					if(Chess.game.board[i][j].capacity!= null)
						return false;
					i--;
					j--;
				}
			}
			return true;
		}
			
		return false;

	}
}
