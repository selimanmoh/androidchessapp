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
	
	/*Method that prints board during the game*
	 * 
	 * @param nothing
	 * @return nothing
	 */
	
	public void printBoard()
	{
		int file;
		int rank;
		
		for(rank = 7; rank >= 0; rank--)
		{
			for(file = 0; file < 8; file++)
			{
				ChessPiece temp = board[rank][file].capacity;

				if(temp instanceof Rook)
				{
					if(temp.getColor() == 0)
						System.out.print("bR ");		
					else if(temp.getColor() == 1)
					 	System.out.print("wR ");		
				}
				
				else if(temp instanceof Knight)
				{
					if(temp.getColor() == 0)
						System.out.print("bN ");		
					else if(temp.getColor() == 1)
					 	System.out.print("wN ");		
				}
				
				else if(temp instanceof Bishop)
				{
					if(temp.getColor() == 0)
						System.out.print("bB ");		
					else if(temp.getColor() == 1)
					 	System.out.print("wB ");		
				}
				
				else if(temp instanceof Queen)
				{
					if(temp.getColor() == 0)
						System.out.print("bQ ");		
					else if(temp.getColor() == 1)
					 	System.out.print("wQ ");		
				}
				
				else if(temp instanceof King)
				{
					if(temp.getColor() == 0)
						System.out.print("bK ");		
					else if(temp.getColor() == 1)
					 	System.out.print("wK ");		
				}
				
				else if(temp instanceof Pawn)
				{
					if(temp.getColor() == 0)
						System.out.print("bp ");		
					else if(temp.getColor() == 1)
					 	System.out.print("wp ");		
				}
				
				else if(temp == null)
				{
					if(((rank%2 == 0) && (file%2 == 0)) || ((rank%2 != 0) && (file%2 !=0)))
						System.out.print("## ");
					else
					 	System.out.print("   ");		
				}
				
			}
			
			System.out.println(rank + 1);
		}
		
		System.out.println(" a  b  c  d  e  f  g  h\n");
		
	}
	
	}