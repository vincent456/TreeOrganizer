package vincenthudry.organizer.model;

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
                        "\tID               INTEGER NOT NULL ,\n" +
                        "\tTitle            TEXT ,\n" +
                        "\tNote             TEXT ,\n" +
                        "\tEncrypted        INTEGER NOT NULL ,\n" +
                        "\tEncryptedData    NONE,\n" +
                        "\tCONSTRAINT Notes_PK PRIMARY KEY (ID)\n" +
                        ")");
        sqLiteDatabase.execSQL("CREATE TABLE Reminder(\n" +
                "\tID     INTEGER NOT NULL ,\n" +
                "\tAlarm  INTEGER NOT NULL ,\n" +
                "\tPRIMARY KEY (ID)\n" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE Node(\n" +
                "\tID         INTEGER NOT NULL ,\n" +
                "\tTitle      TEXT NOT NULL ,\n" +
                "\tID_Parent    INTEGER,\n" +
                "\tCONSTRAINT Node_PK PRIMARY KEY (ID)\n" +
                "\n" +
                "\t,CONSTRAINT Node_Node_FK FOREIGN KEY (ID_Parent) REFERENCES Node(ID)\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
