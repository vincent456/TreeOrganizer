package vincenthudry.organizer.controller;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;

import vincenthudry.organizer.model.Database;
import vincenthudry.organizer.utils.Tuple2;

public class AlarmManager {

    private Context context;
    private Database db;

    private static List<Tuple2<PendingIntent,Long>> intentIDList=new LinkedList<>();

    public AlarmManager(Context context,Database db){
        this.context=context;
        this.db=db;
    }

    public void initAlarms(){
        for(Tuple2<Long,Long> tuple : db.getAllReminders()){
            long time= tuple.t2;
            PendingIntent pendingIntent=setAlarm(time);
            intentIDList.add(new Tuple2<>(pendingIntent, tuple.t1));
        }
    }

    private PendingIntent setAlarm(long time){
        android.app.AlarmManager am= (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context,AlarmReciever.class);
        int ALARM_REQUEST_CODE = 1;
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE,intent,0);
        am.set(android.app.AlarmManager.RTC_WAKEUP,time,pendingIntent);
        return pendingIntent;
    }

    public void addNewAlarm(long time){
        long id=db.addReminder(time);
        PendingIntent pendingIntent=setAlarm(time);
        intentIDList.add(new Tuple2<>(pendingIntent, id));
    }

    public void deleteAlarm(long id){
        for(int i=0;i<intentIDList.size();i++) {
            Tuple2<PendingIntent,Long> tuple=intentIDList.get(i);
            if (tuple.t2 == id) {
                db.deleteReminder(id);
                PendingIntent pendingIntent = tuple.t1;
                pendingIntent.cancel();
                intentIDList.remove(i);
                return;
            }
        }
    }

}
