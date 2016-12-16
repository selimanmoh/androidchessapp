package chess;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by selim on 12/15/2016.
 */

public class Game {
    ArrayList<String> arr;
    String name;
    Date date;

    public Game(ArrayList<String> arr, String name){
        this.arr = arr;
        this.name = name;
        date = Calendar.getInstance().getTime();
    }
}
