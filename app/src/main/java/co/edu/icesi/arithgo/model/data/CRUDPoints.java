package co.edu.icesi.arithgo.model.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import co.edu.icesi.arithgo.app.ArithGoApp;
import co.edu.icesi.arithgo.model.driver.DBDriver;
import co.edu.icesi.arithgo.model.entity.Point;

public class CRUDPoints {

    public static void create(Point point){

        DBDriver driver = DBDriver.getInstance(ArithGoApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();

        String sql = "INSERT INTO $TABLE ($ID, $NAME, $CONTENT) VALUES ('$VID', '$VNAME', '$VPOINTS')";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_POINTS)
                .replace("$ID", DBDriver.POINTS_ID)
                .replace("$NAME", DBDriver.POINTS_NAME)
                .replace("$CONTENT", DBDriver.EARNED_POINTS)
                .replace("$VID", point.getId())
                .replace("$VNAME", point.getName())
                .replace("$VPOINTS", ""+point.getPoints());

        db.execSQL(sql);
        db.close();
    }

    public static Point retrieve(String id){
        DBDriver driver = DBDriver.getInstance(ArithGoApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();
        Point point = null;

        String sql = "SELECT * FROM " + DBDriver.TABLE_POINTS + " WHERE $ID = '$VID'";
        sql = sql
                .replace("$ID", DBDriver.POINTS_ID)
                .replace("$VID", id);
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            String name = cursor.getString (cursor.getColumnIndex(DBDriver.POINTS_NAME));
            Integer points = cursor.getInt(cursor.getColumnIndex(DBDriver.EARNED_POINTS));
            point = new Point(id,name, points);
        }

        return point;
    }


    public static void update(Point point){
        DBDriver driver = DBDriver.getInstance(ArithGoApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();
        String sql = "UPDATE $TABLE SET $NAME = '$FNAME', $CONTENT = '$FPOINTS' WHERE $ID = '$FID'";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_POINTS)
                .replace("$NAME", DBDriver.POINTS_NAME)
                .replace("$CONTENT", DBDriver.EARNED_POINTS)
                .replace("$ID", DBDriver.POINTS_ID)
                .replace("$FNAME", point.getName())
                .replace("$FPOINTS", ""+point.getPoints())
                .replace("$FID", point.getId());
        db.execSQL(sql);
        db.close();
    }

    public static  void delete(Point point){
        DBDriver driver = DBDriver.getInstance(ArithGoApp.getAppContext());
        SQLiteDatabase db = driver.getWritableDatabase();

        String sql = "DELETE FROM $TABLE WHERE $ID = '$FID'";
        sql = sql
                .replace("$TABLE", DBDriver.TABLE_POINTS)
                .replace("$ID", DBDriver.POINTS_ID)
                .replace("$FID", point.getId());
        db.execSQL(sql);
        db.close();
    }


}
