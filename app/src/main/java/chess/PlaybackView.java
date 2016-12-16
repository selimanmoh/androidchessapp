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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import chessController.Bishop;
import chessController.ChessBoard;
import chessController.ChessPiece;
import chessController.King;
import chessController.Knight;
import chessController.Pawn;
import chessController.Queen;
import chessController.Rook;


public class PlaybackView extends AppCompatActivity {
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
	/*
	 * @param args unused
	 * @return nothing
	 */
	public TextView playerTurn;
	public static GridView chessGrid;
	public static ImageAdapter imgAdapter;
	public static ArrayList<String> stringStream;
	public static int streamCounter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playback);
		chessGrid = (GridView) findViewById(R.id.ChessGrid2);
		imgAdapter = new ImageAdapter(this);

		Button nextButton = (Button) findViewById(R.id.buttonNext);

		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				streamCounter++;
				viewBoard();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						PlaybackView.imgAdapter.paintBoard();
						PlaybackView.imgAdapter.notifyDataSetChanged();

					}
				});

			}
		});

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);


		chessGrid.setAdapter(imgAdapter);


	}
	public boolean onOptionsItemSelected(MenuItem item){
		Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
		startActivityForResult(myIntent, 0);
		finish();
		return true;

	}


	@Override
	protected void onStart() {
		super.onStart();
		game = new ChessBoard();

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				PlaybackView.imgAdapter.paintBoard();
				PlaybackView.imgAdapter.notifyDataSetChanged();

			}
		});
		streamCounter = 0;
	}

	public static void getBoard(ArrayList<String> arr){
		stringStream = new ArrayList<String>();
		stringStream.addAll(arr);
	}

	public void viewBoard(){

		for(int i =0; i < streamCounter && i<stringStream.size(); i++){
			PlaybackView.game = StreamChess.streamChess(new ArrayList<String>(stringStream.subList(0, i+1)));
		}

		if(streamCounter>=stringStream.size()) {
			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(), "No more moves!", Toast.LENGTH_SHORT).show();
				}
			});
		}
		PlaybackView.imgAdapter.notifyDataSetChanged();
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
					ChessPiece temp = PlaybackView.game.board[rank][file].capacity;
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


