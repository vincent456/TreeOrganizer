package vincenthudry.organizer;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import vincenthudry.organizer.utils.Tuple2;
import vincenthudry.organizer.view.notes_module.NoteListActivity;
import vincenthudry.organizer.view.todo_view.TodoActivity;

public class Modules {
    public static ArrayList<Tuple2<String,Intent>> data;
    public static void createInstance(Context context){
        data=new ArrayList<>();

        /*
            add each module to data as a new Tuple2<String, Intent>("module name",new Intent(context, ModuleActivity.class));
         */
        Intent notesModule=new Intent(context, NoteListActivity.class);
        data.add(new Tuple2<>("notes", notesModule));

        /*
        Intent timeRemindersModule=new Intent(context, TimeRemidersActivity.class);
        data.add(new Tuple2<String, Intent>("time reminders",timeRemindersModule));
        */
        //TODO: implement reminders


        Intent todoModule = new Intent(context, TodoActivity.class);
        data.add(new Tuple2<>("Todo list", todoModule));
    }
}
