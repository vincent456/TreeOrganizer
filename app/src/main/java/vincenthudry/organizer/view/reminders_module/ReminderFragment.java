package vincenthudry.organizer.view.reminders_module;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import vincenthudry.organizer.R;
import vincenthudry.organizer.controller.AlarmManager;
import vincenthudry.organizer.model.Database;
import vincenthudry.organizer.utils.Tuple2;

public class ReminderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_reminder, container, false);

        //region fab listener
        View fab=v.findViewById(R.id.add_reminder_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Calendar calendar=Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        Calendar calendar=Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,i);
                        calendar.set(Calendar.MINUTE,i1);
                        calendar.set(Calendar.SECOND,0);

                        Database db=new Database(getActivity());

                        AlarmManager alarmManager=new AlarmManager(getContext(),db);
                        alarmManager.addNewAlarm(calendar.getTimeInMillis());

                        RecyclerView rw=v.findViewById(R.id.reminders_list);
                        List<Tuple2<Long,Long>> rwdata=db.getAllReminders();
                        ListAdapter listAdapter=new ListAdapter(rwdata,getActivity(),rw);
                        rw.setAdapter(listAdapter);

                        Toast.makeText(getContext(),"alarm set for "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
            timePickerDialog.show();
            }
        });
        //endregion

        //region populate recyclerView
        Database db=new Database(getActivity());
        RecyclerView rw=v.findViewById(R.id.reminders_list);
        AlarmManager alarmManager=new AlarmManager(getContext(),db);
        alarmManager.initAlarms();
        List<Tuple2<Long,Long>> rwdata=db.getAllReminders();
        ListAdapter listAdapter=new ListAdapter(rwdata,getActivity(),rw);
        rw.setLayoutManager(new LinearLayoutManager(getContext()));
        rw.setAdapter(listAdapter);
        //endregion

        // Inflate the layout for this fragment
        return v;

    }
}