package sl.rakoto.itgrands;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity{
    private TextView buffText;
    private LinearLayout buffLayout, historyLayout;

    private sqlDB sqlDbHelper;
    private SQLiteDatabase db;
    private Cursor dbCursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
;
        historyLayout = (LinearLayout) findViewById(R.id.historyLayout);

        sqlDbHelper = new sqlDB(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = sqlDbHelper.getReadableDatabase();
        dbCursor = db.rawQuery("SELECT * FROM " + sqlDB.TABLE, null);

        historyLayout.removeAllViews();
        loadHistory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        dbCursor.close();
    }

    private void loadHistory(){
        dbCursor.moveToFirst();
        for (int i = 0; i < dbCursor.getCount(); i++) {
            buffLayout = new LinearLayout(this, null);
            buffLayout.setPadding((int)getResources().getDimension(R.dimen.historyTitlePaddingLeft), 0, (int)getResources().getDimension(R.dimen.historyTitlePaddingTop), 0);

            for (int j = 0; j < 3; j++) {
                buffText = (TextView) LayoutInflater.from(this).inflate(R.layout.history_text_view, null);

                if(j == 0) {
                    buffText.setText(dbCursor.getString(dbCursor.getColumnIndex(sqlDB.COLUMN_PHONE)));
                    buffText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.4f));
                }

                if(j == 1) {
                    buffText.setText(dbCursor.getString(dbCursor.getColumnIndex(sqlDB.COLUMN_TIME)));
                    buffText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f));
                }

                if(j == 2) {
                    buffText.setText(dbCursor.getString(dbCursor.getColumnIndex(sqlDB.COLUMN_IMG)));
                    buffText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f));
                }
                buffLayout.addView(buffText);
            }
            if (i % 2 == 1) buffLayout.setBackgroundResource(R.color.historyTableBackground);
            historyLayout.addView(buffLayout);
           if (!dbCursor.isAfterLast()) dbCursor.moveToNext();
        }
    }

}
