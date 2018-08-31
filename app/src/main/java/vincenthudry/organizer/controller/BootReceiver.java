package vincenthudry.organizer.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import vincenthudry.organizer.model.TimeRemindersDatabase;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TimeRemindersDatabase trdb = new TimeRemindersDatabase(context);
        AlarmManager alarmManager = new AlarmManager(context,trdb);
        alarmManager.initAlarms();
    }
}
