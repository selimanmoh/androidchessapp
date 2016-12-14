package chessController;

/*
 *  @author Mohamed Seliman and Mohammad Hadi Uppal
 *  @version 1.0
 *  @since 11-11-2016
 *  
 *  The Queen class implemented like using the Rook and Bishop makeMove methods. 
 */
public class Queen extends ChessPiece {

	public Queen(int color) {
		super(color);
	}
	
	/*
	 * (non-Javadoc)
	 * @see chessController.ChessPiece#makeMove(int, int, int, int)
	 */
	@Override
	public boolean makeMove(int initialX, int initialY, int nextX, int nextY) {
		
		return (new Rook(this.getColor()).makeMove(initialX,initialY,nextX,nextY) || new Bishop(this.getColor()).makeMove(initialX,initialY,nextX,nextY)); 
		}

}
