/*
 * The Chess Program recreates the real life Chess game with enpassant, castling, promotion features implemented
 * alongside the most basic moves. Will display an ascii drawn chess board, with the proper algebraic form for Chess.
 * 
 * @Mohamed Seliman and Mohammad Hadi Uppal
 * Software Methodology Assignment 2
 * @version 1.0
 * @since 11-11-2016
 * 
 */


package chess;

import chessController.Bishop;
import chessController.ChessBoard;
import chessController.ChessPiece;
import chessController.King;
import chessController.Knight;
import chessController.Pawn;
import chessController.Queen;
import chessController.Rook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import java.util.ArrayList;

public class Chess extends AppCompatActivity {
	/*
	 * 
	 * Global variables declared and initialized below. The game is a Chessboard object that contains the 2D array
	 * which acts as the structure of the virtual chess board.
	 * 
	 *  The b/wKingX, b/wKingY integers represent each player's kings for checkmate and check functionality. 
	 *  targetEnpassant is a boolean that represents the gap in moves in which an opposing player can attack
	 *  a vulnerable pawn in the enpassant state. This becomes true immediately after a pawn enters enpassant, only
	 *  becoming false when the player of that pawn uses another move.
	 */
	public static ChessBoard game = new ChessBoard();
	public static int bKingX = 7, bKingY = 4, wKingX = 0, wKingY = 4; //initial positions of kings
	public static boolean targetEnpassant = false;
	public static boolean pausedGame = true;
	public static boolean clicked = false;
	public static String initialCoords;
	public static String nextCoords;
	public static int turn;
	public static boolean undoed = false;
	public static int undoCount = 0;
	public static String input;
	public static boolean draw = false;
	public static boolean gameContinue = true;


	/*
	 * @param args unused
	 * @return nothing
	 */
	public TextView playerTurn;
	public static GridView chessGrid;
	public static ImageAdapter imgAdapter;
	public static ArrayList<String> inputs = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		playerTurn = (TextView) findViewById(R.id.playerTurn);
		chessGrid = (GridView) findViewById(R.id.ChessGrid);
		imgAdapter = new ImageAdapter(this);
		Button undoButton = (Button) findViewById(R.id.buttonUndo);
		Button aiButton = (Button) findViewById(R.id.buttonAI);
		Button drawButton = (Button) findViewById(R.id.buttonDraw);
		Button resignButton = (Button) findViewById(R.id.buttonResign);

		chessGrid.setAdapter(imgAdapter);
		chessGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(clicked){
					clicked = false;
					nextCoords = "" + (position%8) + ((int)Math.floor((63-position)/8)) + "";
					pausedGame = false;
				}
				else {
					clicked = true;
					pausedGame = true;
					initialCoords = "" + (position%8) + ((int)Math.floor((63-position)/8)) + "";
				}

			}
		});

		undoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (undoCount == 0) {
					undoCount++;
					undoed = true;
					pausedGame = false;
					if (inputs.size() > 0) {
						inputs.remove(inputs.size() - 1);
						Chess.game = StreamChess.streamChess(inputs);
						Chess.game.printBoard();


						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								imgAdapter.paintBoard();
								imgAdapter.notifyDataSetChanged();

							}
						});

					}
				} else {
					Toast.makeText(getApplicationContext(), "Cannot undo more than once per turn!", Toast.LENGTH_LONG).show();
				}
			}
		});

		aiButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				ArrayList<ChessPiece> arrRandom = new ArrayList<ChessPiece>();
				boolean looking = true;
				while(looking){
					for(int x = 0; x < 8; x++){
						for(int y = 0; y < 8; y++){
							if(Chess.game.board[x][y].capacity!=null && Chess.game.board[x][y].capacity.getColor() == turn){
								arrRandom.add(Chess.game.board[x][y].capacity);
							}
						}
					}

					ChessPiece randomPiece = arrRandom.get((int)(Math.random()*arrRandom.size()));
					outerloop:
					for(int i = 0; i < 8*Math.random(); i++){
						for(int j = 0; j < 8*Math.random(); j++){
							for(int n = 0; n < 8*Math.random(); n++){
								for(int m = 0; m < 8*Math.random(); m++){
									if(Chess.game.board[i][j].capacity == randomPiece && randomPiece.makeMove(i,j,n,m)){
										looking = false;
										initialCoords = "" + j + i;
										nextCoords = "" + m + n;
										pausedGame = false;
										break outerloop;
									}
								}
							}
						}
					}
				}
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							imgAdapter.paintBoard();
							imgAdapter.notifyDataSetChanged();

							}
						});

			}
		});

		drawButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (draw) {
					Toast.makeText(getApplicationContext(), "The game ends in a draw!", Toast.LENGTH_LONG).show();
					gameContinue = false;
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							playerTurn.post(new Runnable() {
								@Override
								public void run() {
									playerTurn.setText("Draw!");
								}
							});

						}
					});
				}
				else {
					draw = true;
					Toast.makeText(getApplicationContext(), "The previous player has initiated a draw!", Toast.LENGTH_SHORT).show();
				}
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								imgAdapter.paintBoard();
								imgAdapter.notifyDataSetChanged();

							}
						});


				}

		});

		Thread gameThread = new Thread(gameRun);
		gameThread.start();
	}


	Runnable gameRun = new Runnable() {

		@Override
		public void run() {
		/*
		 * turn represents the turn of the game, in which player White is 1 and player Black is 0.
		 */
			turn = 1;
			int initialX;
			int initialY;
			int nextX;
			int nextY;

			while (gameContinue) {
				input = "";
				initialCoords = "";
				nextCoords = "";

				if (turn == 1) {  //White Player's Turn
					if(!undoed) {
						playerTurn.post(new Runnable() {
							@Override
							public void run() {
								playerTurn.setText("White's Turn");
							}
						});
					}
					else{
						turn = 0;
						undoed = false;
						continue;
					}

					pausedGame = true;
					while(pausedGame);
					if(undoed || !gameContinue )
						continue;
					input = initialCoords + nextCoords;

					if(input.equals("") && draw)
						continue;

					if (input.equals("resign")) {
						System.out.println("Black wins");
						gameContinue = false;
						continue;
					}

					if (draw) {
						if (input.equals("draw")) {
							System.out.println("Draw");
							gameContinue = false;
							continue;
						}
						draw = false;
					}

					if (input.contains("draw?"))
						draw = true;

					initialY = Character.getNumericValue(input.charAt(0));
					initialX = Character.getNumericValue(input.charAt(1));
					nextY = Character.getNumericValue(input.charAt(2));
					nextX = Character.getNumericValue(input.charAt(3));

					if ((initialX < 0 || initialX > 7) || (initialY < 0 || initialY > 7) || (nextX < 0 || nextX > 7) || (nextY < 0 || nextY > 7) || game.board[initialX][initialY].capacity == null || game.board[initialX][initialY].capacity.getColor() != turn || !game.board[initialX][initialY].capacity.makeMove(initialX, initialY, nextX, nextY)) {
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getApplicationContext(), "Illegal move, try again!", Toast.LENGTH_LONG).show();
							}
						});
						draw = false;
						continue;
					}
					if (Chess.game.board[nextX][nextY].capacity instanceof King) {
						System.out.println("White wins");
						gameContinue = false;
						continue;
					}

					ChessPiece temp = Chess.game.board[nextX][nextY].capacity; //3,5store eliminated piece here, null or not
					ChessPiece temp2 = Chess.game.board[initialX][nextY].capacity;// 1,5
					ChessPiece temp3 = Chess.game.board[initialX][7].capacity; //1,7
					ChessPiece temp4 = Chess.game.board[initialX][0].capacity; //1,0

					int tempbKingX = bKingX, tempbKingY = bKingY, tempwKingX = wKingX, tempwKingY = wKingY;

					if (nextX == 7 && Chess.game.board[initialX][initialY].capacity instanceof Pawn) {
						//makes pawn a piece by promotion
						if (input.endsWith("B"))
							Chess.game.board[nextX][nextY].capacity = new Bishop(1);
						else if (input.endsWith("N"))
							Chess.game.board[nextX][nextY].capacity = new Knight(1);
						else if (input.endsWith("R"))
							Chess.game.board[nextX][nextY].capacity = new Rook(1);
						else
							Chess.game.board[nextX][nextY].capacity = new Queen(1);

					} else if (Chess.game.board[initialX][initialY].capacity instanceof King && //castling condition
							((King) Chess.game.board[initialX][initialY].capacity).castling) {
						if (nextY > initialY) {
							Chess.game.board[initialX][nextY - 1].capacity = Chess.game.board[initialX][7].capacity;
							Chess.game.board[initialX][7].capacity = null;
						} else if (nextY < initialY) { //move right for castling
							Chess.game.board[initialX][nextY + 1].capacity = Chess.game.board[initialX][0].capacity;
							Chess.game.board[initialX][0].capacity = null;
						}
						Chess.game.board[nextX][nextY].capacity = Chess.game.board[initialX][initialY].capacity;
						((King) Chess.game.board[initialX][initialY].capacity).castling = false;
					} else
						Chess.game.board[nextX][nextY].capacity = Chess.game.board[initialX][initialY].capacity;

					if (targetEnpassant) { //Pawn tries to eliminate an enemy pawn in enpassant condition
						Chess.game.board[initialX][nextY].capacity = null;
						targetEnpassant = false;
					}

					Chess.game.board[initialX][initialY].capacity = null;

					if (checkCondition(1)) { //This move will cause a check condition and should be illegal
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getApplicationContext(), "Illegal move, try again!", Toast.LENGTH_LONG).show();
							}
						});
						draw = false;

						Chess.game.board[initialX][initialY].capacity = Chess.game.board[nextX][nextY].capacity;
						Chess.game.board[nextX][nextY].capacity = temp;
						Chess.game.board[initialX][nextY].capacity = temp2;
						Chess.game.board[initialX][7].capacity = temp3;
						Chess.game.board[initialX][0].capacity = temp4;


						targetEnpassant = true;
						bKingX = tempbKingX;
						bKingY = tempbKingY;
						wKingX = tempwKingX;
						wKingY = tempwKingY;
						continue;
					}
					enpassantReset(0); //changes all current enpassant conditions to false for enemy pawns since a move was made

					turn = 0;
					undoCount = 0;
					inputs.add(input);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							imgAdapter.paintBoard();
							imgAdapter.notifyDataSetChanged();

						}
					});


					if (Chess.checkCondition(0)) {
						if (Chess.checkmateCondition(0)) {
							System.out.println("Checkmate");
							System.out.println("White wins");
							gameContinue = false;
							continue;
						}
						System.out.println("Check");
					}
				} else { //Black Player's Turn
					if(!undoed) {
						playerTurn.post(new Runnable() {
							@Override
							public void run() {
								playerTurn.setText("Black's Turn");
							}
						});
					}
					else{
						turn = 1;
						undoed = false;
						continue;
					}

					pausedGame = true;
					while(pausedGame);
					if(undoed || !gameContinue)
						continue;

					input = initialCoords + nextCoords;

					if(input.equals("") && draw)
						continue;

					if (input.equals("resign")) {
						System.out.println("White wins");
						gameContinue = false;
						continue;
					}

					if (draw) {
						if (input.equals("draw")) {
							System.out.println("Draw");
							gameContinue = false;
							continue;
						}
						draw = false;
					}

					if (input.contains("draw?"))
						draw = true;

					initialY = Character.getNumericValue(input.charAt(0));
					initialX = Character.getNumericValue(input.charAt(1));
					nextY = Character.getNumericValue(input.charAt(2));
					nextX = Character.getNumericValue(input.charAt(3));

					if ((initialX < 0 || initialX > 7) || (initialY < 0 || initialY > 7) || (nextX < 0 || nextX > 7) || (nextY < 0 || nextY > 7) || game.board[initialX][initialY].capacity == null || game.board[initialX][initialY].capacity.getColor() != turn || !game.board[initialX][initialY].capacity.makeMove(initialX, initialY, nextX, nextY)) {
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getApplicationContext(), "Illegal move, try again!", Toast.LENGTH_LONG).show();
							}
						});
						draw = false;
						continue;
					}

					if (Chess.game.board[nextX][nextY].capacity instanceof King) {
						System.out.println("Black wins");
						gameContinue = false;
						continue;
					}
					ChessPiece temp = Chess.game.board[nextX][nextY].capacity; //store eliminated piece here, null or not
					ChessPiece temp2 = Chess.game.board[initialX][nextY].capacity;
					ChessPiece temp3 = Chess.game.board[initialX][7].capacity; //1,7
					ChessPiece temp4 = Chess.game.board[initialX][0].capacity; //1,0

					int tempbKingX = bKingX, tempbKingY = bKingY, tempwKingX = wKingX, tempwKingY = wKingY;


					if (nextX == 0 && Chess.game.board[initialX][initialY].capacity instanceof Pawn) {
						//makes pawn a piece by promotion
						if (input.endsWith("B"))
							Chess.game.board[nextX][nextY].capacity = new Bishop(0);
						else if (input.endsWith("N"))
							Chess.game.board[nextX][nextY].capacity = new Knight(0);
						else if (input.endsWith("R"))
							Chess.game.board[nextX][nextY].capacity = new Rook(0);
						else
							Chess.game.board[nextX][nextY].capacity = new Queen(0);

					} else if (Chess.game.board[initialX][initialY].capacity instanceof King && //castling condition
							((King) Chess.game.board[initialX][initialY].capacity).castling) {
						if (nextY > initialY) {
							Chess.game.board[initialX][nextY - 1].capacity = Chess.game.board[initialX][7].capacity;
							Chess.game.board[initialX][7].capacity = null;
						} else if (nextY < initialY) { //move right for castling
							Chess.game.board[initialX][nextY + 1].capacity = Chess.game.board[initialX][0].capacity;
							Chess.game.board[initialX][0].capacity = null;
						}
						Chess.game.board[nextX][nextY].capacity = Chess.game.board[initialX][initialY].capacity;
						((King) Chess.game.board[initialX][initialY].capacity).castling = false;
					} else
						Chess.game.board[nextX][nextY].capacity = Chess.game.board[initialX][initialY].capacity;


					if (targetEnpassant) { //Pawn tries to eliminate an enemy pawn in enpassant condition
						Chess.game.board[initialX][nextY].capacity = null;
						targetEnpassant = false;
					}

					Chess.game.board[initialX][initialY].capacity = null;
					if (checkCondition(0)) { //This move will cause a check condition and should be illegal
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getApplicationContext(), "Illegal move, try again!", Toast.LENGTH_LONG).show();
							}
						});
						draw = false;
						Chess.game.board[initialX][initialY].capacity = Chess.game.board[nextX][nextY].capacity;
						Chess.game.board[nextX][nextY].capacity = temp;
						Chess.game.board[initialX][nextY].capacity = temp2;
						Chess.game.board[initialX][7].capacity = temp3;
						Chess.game.board[initialX][0].capacity = temp4;

						targetEnpassant = true;
						bKingX = tempbKingX;
						bKingY = tempbKingY;
						wKingX = tempwKingX;
						wKingY = tempwKingY;
						continue;
					}
					enpassantReset(1); //changes all enpassant conditions of enemy pawns to false since a move was made

					turn = 1;
					undoCount = 0;
					inputs.add(input);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							imgAdapter.paintBoard();
							imgAdapter.notifyDataSetChanged();

						}
					});

					if (Chess.checkCondition(1)) {
						if (Chess.checkmateCondition(1)) {
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
	};

	/*
	 * Resets all enpassant states for each pawn on a certain color's side.
	 * 
	 * @param color This is the color that will have all its pawns' enpassant field set to false 
	 * @return nothing
	 */
	public static void enpassantReset(int color){ //resets enpassant for a Player color
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(Chess.game.board[i][j].capacity instanceof Pawn && Chess.game.board[i][j].capacity.getColor() == color)
					((Pawn)Chess.game.board[i][j].capacity).enpassant = false;
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
			x = Chess.wKingX;
			y = Chess.wKingY;
		}
		else{ //check if black is in check
			x = Chess.bKingX;
			y = Chess.bKingY;
		}
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				ChessPiece temp = Chess.game.board[i][j].capacity;
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
				ChessPiece temp = Chess.game.board[i][j].capacity;
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

	public class ImageAdapter extends BaseAdapter
	{
		private Context context;
		Integer[] imageList = new Integer[64];

		public ImageAdapter(Context c)
		{
			context = c;
			paintBoard();

		}

		public void paintBoard(){
			int rank;
			int file;

			for(rank = 0; rank < 8; rank++) {
				for (file = 0; file < 8; file++) {
					ChessPiece temp = Chess.game.board[rank][file].capacity;
					int positionAbs;

					positionAbs = (7 - rank) * 8;
					positionAbs+= (file);

					if (temp instanceof Rook) {
						if (temp.getColor() == 0)
							imageList[positionAbs] = R.drawable.blackrook;
						else if (temp.getColor() == 1)
							imageList[positionAbs] = R.drawable.whiterook;
					} else if (temp instanceof Knight) {
						if (temp.getColor() == 0)
							imageList[positionAbs] = R.drawable.blackknight;
						else if (temp.getColor() == 1)
							imageList[positionAbs] = R.drawable.whiteknight;
					} else if (temp instanceof Bishop) {
						if (temp.getColor() == 0)
							imageList[positionAbs] = R.drawable.blackbishop;
						else if (temp.getColor() == 1)
							imageList[positionAbs] = R.drawable.whitebishop;
					} else if (temp instanceof Queen) {
						if (temp.getColor() == 0)
							imageList[positionAbs] = R.drawable.blackqueen;
						else if (temp.getColor() == 1)
							imageList[positionAbs] = R.drawable.whitequeen;
					} else if (temp instanceof King) {
						if (temp.getColor() == 0)
							imageList[positionAbs] = R.drawable.blackking;
						else if (temp.getColor() == 1)
							imageList[positionAbs] = R.drawable.whiteking;
					} else if (temp instanceof Pawn) {
						if (temp.getColor() == 0)
							imageList[positionAbs] = R.drawable.blackpawn;
						else if (temp.getColor() == 1)
							imageList[positionAbs] = R.drawable.whitepawn;
					} else if (temp == null) {
						imageList[positionAbs] = R.drawable.emptyspot;
					}

				}
			}

		}

		//---returns the number of images---
		public int getCount() {
			return imageList.length;
		}

		//---returns the ID of an item---
		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		//---returns an ImageView view---
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(context);
				imageView.setLayoutParams(new GridView.LayoutParams(90, 90));
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setPadding(0, 0, 0, 0);
			} else {
				imageView = (ImageView) convertView;
			}
			if(imageList[position]!=null) {
				imageView.setImageResource(imageList[position]);
				if((position%8)%2 == (((int)Math.floor((63-position)/8)))%2)
					imageView.setBackgroundColor(getResources().getColor(R.color.Black));
				else
					imageView.setBackgroundColor(getResources().getColor(R.color.White));
			}
			return imageView;
		}
	}

}


