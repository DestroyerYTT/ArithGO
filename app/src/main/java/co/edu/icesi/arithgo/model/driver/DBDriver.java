package co.edu.icesi.arithgo.model.driver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBDriver extends SQLiteOpenHelper {

    private static final String DBName = "Points";
    private static final int DBVersion = 1;
    private static DBDriver instance;

    public static final String TABLE_POINTS = "Points";
    public static final String POINTS_ID = "id";
    public static final String POINTS_NAME = "points";
    public static final String EARNED_POINTS = "earned_points";

    private DBDriver(Context context){
        super(context, DBName, null, DBVersion);
    }

  public static  synchronized DBDriver getInstance(Context context){
    if(instance == null){
        instance = new DBDriver(context);
    }
    return instance;
  }



    @Override
    public void onCreate(SQLiteDatabase db) {
    //CREATE TABLE
        String CREATE_TABLE_POINTS = "CREATE TABLE $TABLE($ID TEXT PRIMARY KEY, $NAME TEXT, $EARNED_POINTS INTEGER)";
        CREATE_TABLE_POINTS = CREATE_TABLE_POINTS
        .replace("$TABLE", TABLE_POINTS)
        .replace("$ID", POINTS_ID)
        .replace("$NAME", POINTS_NAME)
        .replace("$EARNED_POINTS", EARNED_POINTS);

        db.execSQL(CREATE_TABLE_POINTS);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINTS);
        onCreate(db);
    }
}
