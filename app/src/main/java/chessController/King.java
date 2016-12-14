/*
 * 
 * @author Mohamed Seliman and Mohammad Hadi Uppal
 * @version 1.0
 * @since 11-11-2016
 * 
 *King Piece class with castling and start boolean fields. Fields that help implement the
 * castling functionality in Chess.
 */

package chessController;
import chess.Chess;

public class King extends ChessPiece {

	public boolean castling = false;
	public boolean start = true;
	
	public King(int color){
		super(color);
	}
	/*
	 * (non-Javadoc)
	 * @see chessController.ChessPiece#makeMove(int, int, int, int)
	 */
	@Override
	public boolean makeMove(int initialX, int initialY, int nextX, int nextY) {
		
	
		if(Math.abs(nextY-initialY) <= 1 && Math.abs(nextX-initialX) <= 1)
		{
			if(Chess.game.board[nextX][nextY].capacity!= null && Chess.game.board[nextX][nextY].capacity.getColor() == this.getColor())
				return false;
			
			if(this.getColor() == 1){ //Updates place of kings
				Chess.wKingX = nextX;
				Chess.wKingY = nextY;
			}
			else{
				Chess.bKingX = nextX;
				Chess.bKingY = nextY;
			}
			start = false;
			return true;
		}
		else if(initialX - nextX == 0 && this.start && Math.abs(nextY-initialY) == 2 && 
				Chess.game.board[initialX][0].capacity!=null && 
				Chess.game.board[initialX][0].capacity.getColor() == this.getColor() 
				&& Chess.game.board[initialX][0].capacity instanceof Rook &&
				((Rook)Chess.game.board[initialX][0].capacity).start){ //Left castling implementation, King moves left/right 2 spots
			if(Chess.checkCondition(this.getColor())) //King is in check
				return false;
			
			int j;
			if(nextY>initialY){ //checks if there's anything in between the rook and king
				j = initialY + 1;
				while(j!=nextY){
					if(Chess.game.board[initialX][j].capacity != null)
						return false;
					j++;
				}
			}
			else if(nextY< initialY){
				j = initialY - 1;
				while(j!=nextY){
					if(Chess.game.board[initialX][j].capacity != null)
						return false;
					j--;
				}
			}
			if(this.getColor() == 1){ //Updates place of kings
				Chess.wKingX = nextX;
				Chess.wKingY = nextY;
			}
			else{
				Chess.bKingX = nextX;
				Chess.bKingY = nextY;
			}
			castling = true;
			start = false;
			return true;
		}
		else if(initialX - nextX == 0 && this.start && Math.abs(nextY-initialY) == 2 && 
				Chess.game.board[initialX][7].capacity!=null && 
				Chess.game.board[initialX][7].capacity.getColor() == this.getColor() 
				&& Chess.game.board[initialX][7].capacity instanceof Rook &&
				((Rook)Chess.game.board[initialX][7].capacity).start){ //right castling implementation, King moves left/right 2 spots
			if(Chess.checkCondition(this.getColor()))
				return false;
			
			int j;
			if(nextY>initialY){ //checks if there's anything in between the rook and king
				j = initialY + 1;
				while(j!=nextY){
					if(Chess.game.board[initialX][j].capacity != null)
						return false;
					j++;
				}
			}
			else if(nextY< initialY){
				j = initialY - 1;
				while(j!=nextY){
					if(Chess.game.board[initialX][j].capacity != null)
						return false;
					j--;
				}
			}
			if(this.getColor() == 1){ //Updates place of kings
				Chess.wKingX = nextX;
				Chess.wKingY = nextY;
			}
			else{
				Chess.bKingX = nextX;
				Chess.bKingY = nextY;
			}
			castling = true;
			start = false;
			return true;
		}
		
		return false;
		
	}
	
}
