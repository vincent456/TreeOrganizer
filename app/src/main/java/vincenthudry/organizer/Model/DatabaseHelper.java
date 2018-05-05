package vincenthudry.organizer.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context,String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE Notes(\n" +
                "\tID     INTEGER NOT NULL ,\n" +
                "\tTitle  TEXT ,\n" +
                "\tNote   TEXT ,\n" +
                "\tPRIMARY KEY (ID)\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
