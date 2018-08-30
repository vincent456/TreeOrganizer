package vincenthudry.organizer.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.LinkedList;
import java.util.List;

import vincenthudry.organizer.utils.Tuple2;

public class TimeRemindersDatabase extends Database {
    public TimeRemindersDatabase(Context context) {
        super(context);
    }

    //CRUD
    public long addReminder(long timestamp){
        ContentValues values=new ContentValues();
        values.put("Alarm",timestamp);
        return db.insert("Reminder",null,values);
    }
    public long getReminder(long id){
        Cursor cursor = db.query("Reminder",new String[]{"Alarm"},"id=?",new String[]{String.valueOf(id)},null,null,null);
        long out=cursor.getLong(0);
        cursor.close();
        return out;
    }
    //no need to create update method
    public void deleteReminder(long id){
        db.delete("Reminder","id=?",new String[]{String.valueOf(id)});
    }

    public List<Tuple2<Long,Long>> getAllReminders(){
        Cursor cursor=db.query("Reminder",new String[]{"ID,Alarm"},null,null,null,null,"Alarm");
        List<Tuple2<Long,Long>> out = new LinkedList<>();
        while (cursor.moveToNext()){
            out.add(new Tuple2<>(cursor.getLong(0),cursor.getLong(1)));
        }
        cursor.close();
        return out;
    }

}
