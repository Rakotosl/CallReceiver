package sl.rakoto.itgrands;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by GreatPlace on 04.05.2017.
 */

public class Receiver extends BroadcastReceiver {

    public String getTime(){
        Date time = new Date();
        DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");


        String callTime = formatTime.format(time);
        return callTime;
    }

    public int compareTime(String str){
        String[] buff = str.split(":");
        int num = Integer.valueOf(buff[0])*3600 + Integer.valueOf(buff[1])*60 + Integer.valueOf(buff[2]);

        return num;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            final String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            SystemClock.sleep(500);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        sqlDB sqlDbHelper = new sqlDB(context);
                        SQLiteDatabase db = sqlDbHelper.getWritableDatabase();

                        Cursor dbCursor = db.rawQuery("SELECT * FROM " + sqlDB.TABLE, null);
                        dbCursor.moveToLast();
                        if (dbCursor.getCount() >= 0) {
                            if (dbCursor.getCount()== 0 || compareTime(dbCursor.getString(dbCursor.getColumnIndex(sqlDB.COLUMN_TIME))) + 3 < compareTime(getTime())) {
                                int imgNum = new Random().nextInt(3) + 1;
                                db.execSQL("INSERT INTO " + sqlDB.TABLE + " (" + sqlDB.COLUMN_PHONE + ", " + sqlDB.COLUMN_TIME + ", " + sqlDB.COLUMN_IMG + ") " + "VALUES ('" + incomingNumber + "', '" + getTime() + "', " + imgNum + ");");
                                db.close();

                                Intent ad = new Intent(context, Ad.class);
                                ad.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(ad);
                            }
                        }
                    }
                }).start();

        }

    }
}
