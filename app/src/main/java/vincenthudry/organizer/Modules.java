package vincenthudry.organizer;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import vincenthudry.organizer.utils.Tuple2;
import vincenthudry.organizer.view.notes_module.NoteListActivity;

public class Modules {
    public static ArrayList<Tuple2<String,Intent>> data;
    public static void createInstance(Context context){
        data=new ArrayList<>();

        Intent notesModule=new Intent(context, NoteListActivity.class);
        data.add(new Tuple2<String, Intent>("notes",notesModule));
    }
}
