package chess;

import java.util.ArrayList;

import chessController.Bishop;
import chessController.ChessBoard;
import chessController.ChessPiece;
import chessController.King;
import chessController.Knight;
import chessController.Pawn;
import chessController.Queen;
import chessController.Rook;
import chessController.ChessBoard;

/**
 * Created by Mohamed Seliman on 12/15/2016.
 */

public class StreamChess {



    public static ChessBoard game;
    public static int bKingX = 7, bKingY = 4, wKingX = 0, wKingY = 4; //initial positions of kings
    public static boolean targetEnpassant = false;
    public static int counterInput;
	/*
	 * @param args inputs
	 * @return ChessBoard
	 */
    public static ChessBoard streamChess(ArrayList<String> inputs){
        counterInput = 0;
        game = new ChessBoard();
        Chess.game.board = StreamChess.game.board;
        play(inputs);
        System.out.println(inputs.size());
        return StreamChess.game;
    }

    public static void play(ArrayList<String> inputs) {
		/*
		 * turn represents the turn of the game, in which player White is 1 and player Black is 0.
		 */
        int turn = 1;
        int initialX;
        int initialY;
        int nextX;
        int nextY;
        boolean gameContinue = true;
        boolean draw = false;

        String input;

        while(gameContinue)
        {

            if(turn == 1){  //White Player's Turn
                if(counterInput < inputs.size()) {
                    input = inputs.get(counterInput);
                    counterInput++;
                }
                else
                    return;


                input = input.replaceAll("\\s+", "");

                if(input.equals("resign")){
                    System.out.println("Black wins");
                    gameContinue = false;
                    continue;
                }

                if(draw){
                    if(input.equals("draw")){
                        System.out.println("Draw");
                        gameContinue = false;
                        continue;
                    }
                    draw = false;
                }

                if(input.contains("draw?"))
                    draw = true;

                initialY = Character.getNumericValue(input.charAt(0));
                initialX = Character.getNumericValue(input.charAt(1));
                nextY = Character.getNumericValue(input.charAt(2));
                nextX = Character.getNumericValue(input.charAt(3));

                if((initialX < 0 || initialX > 7) || (initialY < 0 || initialY > 7) || (nextX < 0 || nextX > 7) || (nextY < 0 || nextY > 7) || game.board[initialX][initialY].capacity == null || game.board[initialX][initialY].capacity.getColor()!=turn || !game.board[initialX][initialY].capacity.makeMove(initialX, initialY, nextX, nextY)){
                    System.out.println("Illegal move, try again\n");
                    draw = false;
                    continue;
                }
                if(StreamChess.game.board[nextX][nextY].capacity instanceof King){
                    System.out.println("White wins");
                    gameContinue = false;
                    continue;
                }

                ChessPiece temp = StreamChess.game.board[nextX][nextY].capacity; //3,5store eliminated piece here, null or not
                ChessPiece temp2 = StreamChess.game.board[initialX][nextY].capacity;// 1,5
                ChessPiece temp3 = StreamChess.game.board[initialX][7].capacity; //1,7
                ChessPiece temp4 = StreamChess.game.board[initialX][0].capacity; //1,0

                int tempbKingX = bKingX, tempbKingY = bKingY, tempwKingX =wKingX, tempwKingY = wKingY;

                if(nextX == 7 && StreamChess.game.board[initialX][initialY].capacity instanceof Pawn){
                    //makes pawn a piece by promotion
                    if(input.endsWith("B"))
                        StreamChess.game.board[nextX][nextY].capacity = new Bishop(1);
                    else if(input.endsWith("N"))
                        StreamChess.game.board[nextX][nextY].capacity = new Knight(1);
                    else if(input.endsWith("R"))
                        StreamChess.game.board[nextX][nextY].capacity = new Rook(1);
                    else
                        StreamChess.game.board[nextX][nextY].capacity = new Queen(1);

                }
                else if(StreamChess.game.board[initialX][initialY].capacity instanceof King && //castling condition
                        ((King)StreamChess.game.board[initialX][initialY].capacity).castling){
                    if(nextY > initialY){
                        StreamChess.game.board[initialX][nextY-1].capacity = StreamChess.game.board[initialX][7].capacity;
                        StreamChess.game.board[initialX][7].capacity = null;
                    }
                    else if(nextY < initialY){ //move right for castling
                        StreamChess.game.board[initialX][nextY+1].capacity = StreamChess.game.board[initialX][0].capacity;
                        StreamChess.game.board[initialX][0].capacity = null;
                    }
                    StreamChess.game.board[nextX][nextY].capacity = StreamChess.game.board[initialX][initialY].capacity;
                    ((King)StreamChess.game.board[initialX][initialY].capacity).castling = false;
                }
                else
                    StreamChess.game.board[nextX][nextY].capacity = StreamChess.game.board[initialX][initialY].capacity;

                if(targetEnpassant){ //Pawn tries to eliminate an enemy pawn in enpassant condition
                    StreamChess.game.board[initialX][nextY].capacity = null;
                    targetEnpassant = false;
                }

                StreamChess.game.board[initialX][initialY].capacity = null;

                if(checkCondition(1)){ //This move will cause a check condition and should be illegal
                    System.out.println("Illegal move, try again\n");
                    draw = false;

                    StreamChess.game.board[initialX][initialY].capacity = StreamChess.game.board[nextX][nextY].capacity;
                    StreamChess.game.board[nextX][nextY].capacity = temp;
                    StreamChess.game.board[initialX][nextY].capacity = temp2;
                    StreamChess.game.board[initialX][7].capacity = temp3;
                    StreamChess.game.board[initialX][0].capacity = temp4;


                    targetEnpassant = true;
                    bKingX = tempbKingX;
                    bKingY = tempbKingY;
                    wKingX = tempwKingX;
                    wKingY = tempwKingY;
                    continue;
                }
                enpassantReset(0); //changes all current enpassant conditions to false for enemy pawns since a move was made
                turn = 0;

                if(StreamChess.checkCondition(0)){
                    if(StreamChess.checkmateCondition(0)){
                        System.out.println("Checkmate");
                        System.out.println("White wins");
                        gameContinue = false;
                        continue;
                    }
                    System.out.println("Check");
                }
            }
            else{ //Black Player's Turn
                if(counterInput < inputs.size()) {
                    input = inputs.get(counterInput);
                    counterInput++;
                }
                else
                    return;
                input = input.replaceAll("\\s+", "");

                if(input.equals("resign")){
                    System.out.println("White wins");
                    gameContinue = false;
                    continue;
                }

                if(draw){
                    if(input.equals("draw")){
                        System.out.println("Draw");
                        gameContinue = false;
                        continue;
                    }
                    draw = false;
                }

                if(input.contains("draw?"))
                    draw = true;

                initialY = Character.getNumericValue(input.charAt(0));
                initialX = Character.getNumericValue(input.charAt(1));
                nextY = Character.getNumericValue(input.charAt(2));
                nextX = Character.getNumericValue(input.charAt(3));

                if((initialX < 0 || initialX > 7) || (initialY < 0 || initialY > 7) || (nextX < 0 || nextX > 7) || (nextY < 0 || nextY > 7) || game.board[initialX][initialY].capacity == null || game.board[initialX][initialY].capacity.getColor()!=turn || !game.board[initialX][initialY].capacity.makeMove(initialX, initialY, nextX, nextY)){
                    System.out.println("Illegal move, try again\n");
                    draw = false;
                    continue;
                }

                if(StreamChess.game.board[nextX][nextY].capacity instanceof King){
                    System.out.println("Black wins");
                    gameContinue = false;
                    continue;
                }
                ChessPiece temp = StreamChess.game.board[nextX][nextY].capacity; //store eliminated piece here, null or not
                ChessPiece temp2 = StreamChess.game.board[initialX][nextY].capacity;
                ChessPiece temp3 = StreamChess.game.board[initialX][7].capacity; //1,7
                ChessPiece temp4 = StreamChess.game.board[initialX][0].capacity; //1,0

                int tempbKingX = bKingX, tempbKingY = bKingY, tempwKingX =wKingX, tempwKingY = wKingY;


                if(nextX == 0 && StreamChess.game.board[initialX][initialY].capacity instanceof Pawn){
                    //makes pawn a piece by promotion
                    if(input.endsWith("B"))
                        StreamChess.game.board[nextX][nextY].capacity = new Bishop(0);
                    else if(input.endsWith("N"))
                        StreamChess.game.board[nextX][nextY].capacity = new Knight(0);
                    else if(input.endsWith("R"))
                        StreamChess.game.board[nextX][nextY].capacity = new Rook(0);
                    else
                        StreamChess.game.board[nextX][nextY].capacity = new Queen(0);

                }
                else if(StreamChess.game.board[initialX][initialY].capacity instanceof King && //castling condition
                        ((King)StreamChess.game.board[initialX][initialY].capacity).castling){
                    if(nextY > initialY){
                        StreamChess.game.board[initialX][nextY-1].capacity = StreamChess.game.board[initialX][7].capacity;
                        StreamChess.game.board[initialX][7].capacity = null;
                    }
                    else if(nextY < initialY){ //move right for castling
                        StreamChess.game.board[initialX][nextY+1].capacity = StreamChess.game.board[initialX][0].capacity;
                        StreamChess.game.board[initialX][0].capacity = null;
                    }
                    StreamChess.game.board[nextX][nextY].capacity = StreamChess.game.board[initialX][initialY].capacity;
                    ((King)StreamChess.game.board[initialX][initialY].capacity).castling = false;
                }
                else
                    StreamChess.game.board[nextX][nextY].capacity = StreamChess.game.board[initialX][initialY].capacity;


                if(targetEnpassant){ //Pawn tries to eliminate an enemy pawn in enpassant condition
                    StreamChess.game.board[initialX][nextY].capacity = null;
                    targetEnpassant = false;
                }

                StreamChess.game.board[initialX][initialY].capacity = null;
                if(checkCondition(0)){ //This move will cause a check condition and should be illegal
                    System.out.println("Illegal move, try again\n");
                    draw = false;
                    StreamChess.game.board[initialX][initialY].capacity = StreamChess.game.board[nextX][nextY].capacity;
                    StreamChess.game.board[nextX][nextY].capacity = temp;
                    StreamChess.game.board[initialX][nextY].capacity = temp2;
                    StreamChess.game.board[initialX][7].capacity = temp3;
                    StreamChess.game.board[initialX][0].capacity = temp4;

                    targetEnpassant = true;
                    bKingX = tempbKingX;
                    bKingY = tempbKingY;
                    wKingX = tempwKingX;
                    wKingY = tempwKingY;
                    continue;
                }
                enpassantReset(1); //changes all enpassant conditions of enemy pawns to false since a move was made
                turn = 1;

                if(StreamChess.checkCondition(1)){
                    if(StreamChess.checkmateCondition(1)){
                        System.out.println("Checkmate");
                        System.out.println("Black wins");
                        gameContinue = false;
                        continue;
                    }
                    System.out.println("Check");
                }
            }


        }

    }
    /*
     * Resets all enpassant states for each pawn on a certain color's side.
     *
     * @param color This is the color that will have all its pawns' enpassant field set to false
     * @return nothing
     */
    public static void enpassantReset(int color){ //resets enpassant for a Player color

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(StreamChess.game.board[i][j].capacity instanceof Pawn && StreamChess.game.board[i][j].capacity.getColor() == color)
                    ((Pawn)StreamChess.game.board[i][j].capacity).enpassant = false;
            }
        }

    }

	/*
	 * Checks if the color player's king is in check. Does this by checking if any opposing pieces can reach the king.
	 *
	 * @param color This represents the player that will have their king be checked for a check condition
	 * @return boolean This will return true if there indeed is a check condition for the player color, false otherwise
	 */

    public static boolean checkCondition(int color){ //checks if Player color is in check

        int x,y;

        if(color == 1){ //check if white is in check
            x = StreamChess.wKingX;
            y = StreamChess.wKingY;
        }
        else{ //check if black is in check
            x = StreamChess.bKingX;
            y = StreamChess.bKingY;
        }

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                ChessPiece temp = StreamChess.game.board[i][j].capacity;
                if(temp!=null && temp.getColor() != color && temp.makeMove(i, j, x, y))
                    return true;


            }
        }
        return false;

    }

    /*
     * checkmate is like check, but checks if anything can be done to remove the color player from check.
     *
     * @param color This represents the player color who's king will be checked for checkmate.
     * @return boolean This returns true when the player color's king indeed is in checkmate, false otherwise.
     */
    public static boolean checkmateCondition(int color){

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                ChessPiece temp = StreamChess.game.board[i][j].capacity;
                for(int k = 0; k < 8; k++){
                    for(int l = 0; l< 8; l++){
                        if(temp!=null && temp.getColor() == color && temp.makeMove(i, j, k, l) && !checkCondition(color))
                            return false;


                    }
                }
            }
        }

        return true;
    }



}
