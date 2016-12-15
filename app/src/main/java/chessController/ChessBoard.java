/*
 * ChessBoard class which represents the virtual chess board.
 * 
 * @author Mohamed Seliman and Mohammad Hadi Uppal
 * @version 1.0
 * @since 11-11-2016
 */

package chessController;
public class ChessBoard {
	/*
	 * board is a 2D array of Squares and gameStatus checks for certain game conditions like stalemate. 
	 */
	public Square[][] board;
	public int gameStatus;
	
	/*Initializing board and populating pieces*/
	/*
	 * Constructor for Chess Board. Initializes all squares created in the board array.
	 * @param nothing
	 * @return nothing
	 */
	public ChessBoard()
	
	{
		board = new Square[8][8];
		
		//Populating white pieces at the start of the game
		
		board[7][0] = new Square(new Rook(0));
		board[7][1] = new Square(new Knight(0));
		board[7][2] = new Square(new Bishop(0));
		board[7][3] = new Square(new Queen(0));
		board[7][4] = new Square(new King(0));
		board[7][5] = new Square(new Bishop(0));
		board[7][6] = new Square(new Knight(0));
		board[7][7] = new Square(new Rook(0));
		
		board[6][0] = new Square(new Pawn(0));
		board[6][1] = new Square(new Pawn(0));
		board[6][2] = new Square(new Pawn(0));
		board[6][3] = new Square(new Pawn(0));
		board[6][4] = new Square(new Pawn(0));
		board[6][5] = new Square(new Pawn(0));
		board[6][6] = new Square(new Pawn(0));
		board[6][7] = new Square(new Pawn(0));
		
		//Populating black pieces at the start of the game
		
		board[0][0] = new Square(new Rook(1));
		board[0][1] = new Square(new Knight(1));
	    board[0][2] = new Square(new Bishop(1));
		board[0][3] = new Square(new Queen(1));
		board[0][4] = new Square(new King(1));
		board[0][5] = new Square(new Bishop(1));
		board[0][6] = new Square(new Knight(1));
		board[0][7] = new Square(new Rook(1));
		
		board[1][0] = new Square(new Pawn(1));
		board[1][1] = new Square(new Pawn(1));
		board[1][2] = new Square(new Pawn(1));
		board[1][3] = new Square(new Pawn(1));
		board[1][4] = new Square(new Pawn(1));
		board[1][5] = new Square(new Pawn(1));
		board[1][6] = new Square(new Pawn(1));
		board[1][7] = new Square(new Pawn(1));
		
		//Populating the rest of the board, ranks 2 through 6, with squares
		
		for(int i = 2; i < 6; i++)
		{
			for(int j = 0; j < 8; j++)
				board[i][j] = new Square();
		}
		
	}
	
}