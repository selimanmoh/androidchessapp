/*
 *  @author Mohamed Seliman and Mohammad Hadi Uppal
 *  @version 1.0
 *  @since 11-11-2016
 *  
 *  The Pawn class represents the pawn piece in chess. It has an enpassant boolean field for use in the enpassant
 *  functionality in Chess.
 */

package chessController;

import chess.Chess;

public class Pawn extends ChessPiece {
	public boolean enpassant;
	
	public Pawn(int color) {
		super(color);
		// TODO Auto-generated constructor stub
	}
/*
 * (non-Javadoc)
 * @see chessController.ChessPiece#makeMove(int, int, int, int)
 */
	@Override
	public boolean makeMove(int initialX, int initialY, int nextX, int nextY) { //remember no change moves
		
		ChessPiece temp = Chess.game.board[nextX][nextY].capacity;
		
		if(temp != null && temp.getColor() == this.getColor())
			return false;

		if(this.getColor() == 1)
		{
			
			if(nextX-initialX == 1 && Math.abs(nextY-initialY) <= 1) //moving up or diagonally left or right
			{
				if(temp!= null && nextY-initialY == 0) //pawn moves up towards a square that already is filled
					return false;
				else if(temp == null && Math.abs(nextY-initialY) == 1 && initialX == 4 && (Chess.game.board[initialX][nextY].capacity instanceof Pawn && ((Pawn)Chess.game.board[initialX][nextY].capacity).enpassant)){ //enpassant condition with empty square in the diagonal move
					Chess.targetEnpassant = true;
					return true;
				}
				else if(temp == null && Math.abs(nextY-initialY) == 1) //no enpassant condition and moves diagonally to empty square
					return false;
				else
					return true; //when temp is null and it goes straight up or if diagonal is filled it goes diagonal
			}
			else if(initialX == 1 && nextX-initialX == 2 && nextY-initialY == 0) //if pawn moves into enpassant
			{
				if(temp == null){
					enpassant = true;
					return true;
				}
			}
			
			return false;
			
		}
		else{ //Black Pawn
			
			if(initialX-nextX == 1 && Math.abs(nextY-initialY) <= 1) //moving down or diagonally left or right
			{
				if(temp!= null && nextY-initialY == 0) //pawn moves down towards a square that already is filled
					return false;
				else if(temp == null && Math.abs(nextY-initialY) == 1 && initialX == 3 && (Chess.game.board[initialX][nextY].capacity instanceof Pawn && ((Pawn)Chess.game.board[initialX][nextY].capacity).enpassant)){ //enpassant condition with empty square in the diagonal move
					Chess.targetEnpassant = true;
					return true;
				}
				else if(temp == null && Math.abs(nextY-initialY) == 1) //no enpassant condition and moves diagonally to empty square
					return false;
				else
					return true; //when temp is null and it goes straight up or if diagonal is filled it goes diagonal
			}
			else if(initialX == 6 && initialX-nextX == 2 && nextY-initialY == 0) //if pawn moves into enpassant
			{
				if(temp == null){
					enpassant = true;
					return true;
				}
			}
			
			return false;
			
		}
		

	}

}
