package sl.rakoto.itgrands;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GreatPlace on 06.05.2017.
 */

public class sqlDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "callDB";
    private static final int SCHEMA = 1;
    public static final String TABLE = "calls";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_IMG = "img";

    public sqlDB(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table calls ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PHONE + " TEXT, "
            + COLUMN_TIME + " TEXT, "
            + COLUMN_IMG + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
