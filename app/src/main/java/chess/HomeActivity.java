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

import java.util.ArrayList;
import java.util.List;
import android.widget.Adapter;



public class HomeActivity extends AppCompatActivity {

    public static ArrayList<Game> gameInputs = new ArrayList<Game>();
    public static ListView list;
    public static ArrayAdapter<Game> gameAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button playButton = (Button)findViewById(R.id.buttonPlay);
        Button playbackButton = (Button)findViewById(R.id.buttonPlayback);

        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Chess.class));
            }

        });
        gameAdapter = new GameAdapter(this, gameInputs);

        list = (ListView)findViewById(R.id.gameList);

        list.setAdapter(gameAdapter);
    }

    public static void addList(ArrayList<String> arr, String name){
        gameInputs.add(new Game(arr, name));
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

            textView.setText(game.name + game.date.toString());
            return convertView;
        }
    }
}

