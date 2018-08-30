package vincenthudry.organizer.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {
    protected static SQLiteDatabase db;
    protected DatabaseHelper dbhp;
    protected Context context;

    public Database(Context context){
            dbhp = new DatabaseHelper(context);
            this.context = context;
            db = dbhp.getWritableDatabase();
    }

    public void closeDB(){
        db.close();
    }

    public void nukeDB(){
        db.close();
        context.deleteDatabase(db.getPath());
    }
}
