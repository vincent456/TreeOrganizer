package vincenthudry.organizer.view.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import vincenthudry.organizer.R;
import vincenthudry.organizer.model.TimeRemindersDatabase;
import vincenthudry.organizer.utils.Tuple2;

public class ShowAllReminders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_reminders);

        TimeRemindersDatabase trd = new TimeRemindersDatabase(this);


        //region test
        Calendar calendar=Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY,18);
        calendar.set(Calendar.MINUTE,45);
        trd.addReminder(calendar.getTimeInMillis());

        calendar.set(Calendar.HOUR_OF_DAY,18);
        calendar.set(Calendar.MINUTE,30);
        trd.addReminder(calendar.getTimeInMillis());

        //endregion

        RecyclerView rw = findViewById(R.id.all_reminders_list);
        rw.setLayoutManager(new LinearLayoutManager(this));
        List<Tuple2<Long,Long>> allreminders = trd.getAllReminders();
        ReminderAdapter adapter = new ReminderAdapter(allreminders);
        rw.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
