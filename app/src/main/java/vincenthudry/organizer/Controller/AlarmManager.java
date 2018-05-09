package vincenthudry.organizer.Controller;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;

import vincenthudry.organizer.Model.Database;

public class AlarmManager {

    private Context context;
    private Database db;

    private List<PendingIntent> intentList;
    private final int ALARM_REQUEST_CODE=1;

    public AlarmManager(Context context,Database db){
        this.context=context;
        this.db=db;
        this.intentList=new LinkedList<>();
    }

    public void addAlarm(long time){
        android.app.AlarmManager am= (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent();
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,ALARM_REQUEST_CODE,intent,0);
        intentList.add(pendingIntent);
        am.set(android.app.AlarmManager.RTC_WAKEUP,time,pendingIntent);
        db.addReminder(time);
    }

}
