package chess;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;

import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;
import android.widget.Adapter;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;



public class HomeActivity extends AppCompatActivity {

    public static ArrayList<Game> gameInputs = new ArrayList<Game>();
    public static ListView list;
    public static ArrayAdapter<Game> gameAdapter;
    public static ToggleButton toggle;
    public static sortName sN;
    public static sortDate sD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button playButton = (Button)findViewById(R.id.buttonPlay);
        toggle = (ToggleButton)findViewById(R.id.toggleButton);
        sN = new sortName();
        sD = new sortDate();


        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Chess.class));
            }

        });


        gameAdapter = new GameAdapter(this, gameInputs);

        list = (ListView)findViewById(R.id.gameList);

        list.setAdapter(gameAdapter);

        list.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                if (gameAdapter != null) {
                    PlaybackView.getBoard(gameInputs.get(pos).arr);
                    startActivity(new Intent(HomeActivity.this, PlaybackView.class));
                }

                return false;
            }
        });

        toggle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                if(toggle.isChecked()){
                    Collections.sort(gameInputs, sN);
                }
                else
                    Collections.sort(gameInputs, sD);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameAdapter.notifyDataSetChanged();}});

            }
        });
    }

    public static void addList(ArrayList<String> arr, String name){
        gameInputs.add(new Game(arr, name));
        if(toggle.isChecked()){
            Collections.sort(gameInputs, sN);
        }
        else
            Collections.sort(gameInputs, sD);

       toggle.setChecked(true);
    }

    public class GameAdapter extends ArrayAdapter<Game> {

        public GameAdapter(Context context, ArrayList<Game> games) {
            super(context, 0, games);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Game game = getItem(position);
            TextView textView;
            if (convertView == null) {
                convertView = new TextView(HomeActivity.this);
            }

            textView = (TextView) convertView;

            textView.setText(game.name + "          " + game.date.toString());
            textView.setTextSize(20);
            textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            return convertView;
        }
    }

    public class sortName implements Comparator{

        public int compare(Object o1, Object o2) {
            Game game1 = (Game) o1;
            Game game2 = (Game) o2;
            return game1.name.compareTo(game2.name);
        }
    }

    public class sortDate implements Comparator{

        public int compare(Object o1, Object o2) {
            Game game1 = (Game) o1;
            Game game2 = (Game) o2;
            return game1.date.compareTo(game2.date);
        }
    }
}

