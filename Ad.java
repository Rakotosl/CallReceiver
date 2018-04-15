package sl.rakoto.itgrands;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Ad extends Activity {
    private ImageView adImg;
    private TextView adText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setAttributes(new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        ));
        setContentView(R.layout.dialog_view);

        adImg = (ImageView) findViewById(R.id.adImg);
        adText = (TextView) findViewById(R.id.adText);

        sqlOperation();

        this.setFinishOnTouchOutside(true);
    }

    private void sqlOperation(){
        sqlDB sqlDbHelper = new sqlDB(this);
        SQLiteDatabase db = sqlDbHelper.getReadableDatabase();
        Cursor dbCursor = db.rawQuery("SELECT * FROM " + sqlDB.TABLE, null);
        dbCursor.moveToLast();
        switchImg(dbCursor.getInt(dbCursor.getColumnIndex(sqlDB.COLUMN_IMG)), dbCursor.getString(dbCursor.getColumnIndex(sqlDB.COLUMN_PHONE)));
        db.close(); dbCursor.close();
    }

    private void switchImg(int imgNum, String str){
        switch(imgNum){
            case 1:
                adImg.setImageResource(R.drawable.first);
                break;
            case 2:
                adImg.setImageResource(R.drawable.second);
                break;
            case 3:
                adImg.setImageResource(R.drawable.third);
                break;
        }
        adText.setText(str);
    }

}
