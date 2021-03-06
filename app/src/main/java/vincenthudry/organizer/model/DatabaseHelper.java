package vincenthudry.organizer.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import vincenthudry.organizer.Settings;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, Settings.databaseName, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE Notes(\n" +
                        "\tID               INTEGER NOT NULL ,\n" +
                        "\tTitle            TEXT NOT NULL ,\n" +
                        "\tNote             TEXT NOT NULL ,\n" +
                        "\tEncrypted        INTEGER NOT NULL ,\n" +
                        "\tEncryptedData    NONE ,\n" +
                        "\tID_Node          INTEGER,\n" +
                        "\tCONSTRAINT Notes_PK PRIMARY KEY (ID)\n" +
                        "\n" +
                        "\t,CONSTRAINT Notes_Node_FK FOREIGN KEY (ID_Node) REFERENCES Node(ID)\n" +
                        ");");
        sqLiteDatabase.execSQL("CREATE TABLE Reminder(\n" +
                "\tID         INTEGER NOT NULL ,\n" +
                "\tAlarm      INTEGER NOT NULL ,\n" +
                "\tID_Node    INTEGER,\n" +
                "\tCONSTRAINT Reminder_PK PRIMARY KEY (ID)\n" +
                "\n" +
                "\t,CONSTRAINT Reminder_Node_FK FOREIGN KEY (ID_Node) REFERENCES Node(ID)\n" +
                ");");
        sqLiteDatabase.execSQL("CREATE TABLE Node(\n" +
                "\tID         INTEGER NOT NULL ,\n" +
                "\tTitle      TEXT NOT NULL ,\n" +
                "\tID_Parent    INTEGER,\n" +
                "\tCONSTRAINT Node_PK PRIMARY KEY (ID)\n" +
                "\n" +
                "\t,CONSTRAINT Node_Node_FK FOREIGN KEY (ID_Parent) REFERENCES Node(ID)\n" +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE TODO_list(\n" +
                "\tID         INTEGER NOT NULL ,\n" +
                "\tDATA       TEXT NOT NULL ,\n" +
                "\tID_Node    INTEGER,\n" +
                "\tCONSTRAINT TODO_list_PK PRIMARY KEY (ID)\n" +
                "\n" +
                "\t,CONSTRAINT TODO_list_Node_FK FOREIGN KEY (ID_Node) REFERENCES Node(ID)\n" +
                ")");

        try {
            Toast.makeText(context, "Tables created", Toast.LENGTH_SHORT).show();
        }
        catch (RuntimeException e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
