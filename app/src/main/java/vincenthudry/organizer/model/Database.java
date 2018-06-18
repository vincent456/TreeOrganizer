package vincenthudry.organizer.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import vincenthudry.organizer.utils.Tuple2;

public class Database {
    private SQLiteDatabase db;
    private DatabaseHelper dbhp;
    private Context context;

    public Database(Context context,String name){
        dbhp=new DatabaseHelper(context,name);
        this.context=context;
        db=dbhp.getWritableDatabase();
    }

    //region notes
    //region CRUD
    public long addNote(String title,String note){
        ContentValues values=new ContentValues();
        values.put("Title",title);
        values.put("Note",note);
        values.put("Encrypted",false);
        values.put("EncryptedData",new byte[]{});
        return db.insert("Notes",null,values);
    }

    public List<Long> getIdByTitle(String title){
        Cursor cursor=db.query("Notes",new String[]{"ID"},"title=?",new String[]{title},null,null,"ID");
        List<Long> out=new LinkedList<>();
        while (cursor.moveToNext()){
            out.add(cursor.getLong(0));
        }
        cursor.close();
        return out;
    }

    public String getNoteContent(long id){
        Cursor cursor=db.query("Notes",new String[]{"Note"},"id=?",new String[]{String.valueOf(id)},null,null,null,String.valueOf(1));
        cursor.moveToFirst();
        String out = cursor.getString(0);
        cursor.close();
        return out;
    }

    public String getNoteTitle(long id){
        Cursor cursor=db.query("Notes",new String[]{"Title"},"id=?",new String[]{String.valueOf(id)},null,null,null);
        cursor.moveToFirst();
        String out= cursor.getString(0);
        cursor.close();
        return out;
    }

    public void updateNote(long id,String title,String note){
        ContentValues values=new ContentValues();
        values.put("Title",title);
        values.put("Note",note);
        db.update("Notes",values,"id=?",new String[]{String.valueOf(id)});
    }

    public void deleteNote(long id){
        db.delete("Notes","ID=?",new String[]{Long.toString(id)});
    }

    public List<Tuple2<Long,String>> getAllTitles(){
        Cursor cursor=db.query("Notes",new String[]{"ID","Title"},null,null,null,null,"Title");
        List<Tuple2<Long,String>> out=new LinkedList<>();
        while (cursor.moveToNext()){
            out.add(new Tuple2<>(cursor.getLong(0),cursor.getString(1)));
        }
        cursor.close();
        return out;
    }
    //endregion

    //region crypt

     public boolean getEncrypted(long id){
        Cursor cursor=db.query("Notes",new String[]{"Encrypted"},"id=?",new String[]{String.valueOf(id)},null,null,null);
        cursor.moveToFirst();
        boolean isEncrypted=cursor.getLong(0)==1;
        cursor.close();
        return  isEncrypted;
    }

    public void setEncrypted(long id, boolean bool){
        ContentValues values=new ContentValues();
        values.put("Encrypted",bool?1:0);
        db.update("Notes",values,"id=?",new String[]{String.valueOf(id)});
    }

    public void setEncryptedData(long id,byte[] data){
        ContentValues values=new ContentValues();
        values.put("EncryptedData",data);
        db.update("Notes",values,"id=?",new String[]{String.valueOf(id)});
    }

    public byte[] getEncryptedData(long id){
        Cursor cursor = db.query("Notes",new String[]{"EncryptedData"},"id=?",new String[]{String.valueOf(id)},null,null,null);
        cursor.moveToFirst();
        byte[] out = cursor.getBlob(0);
        cursor.close();
        return out;
    }

    public void updateText(long id, String text){
        ContentValues values=new ContentValues();
        values.put("Note",text);
        db.update("Notes",values,"id=?",new String[]{String.valueOf(id)});
    }


    public long addEncryptedNote(String title,String note,byte[] data) {
        ContentValues values=new ContentValues();
        values.put("Title",title);
        values.put("Note",note);
        values.put("Encrypted",true);
        values.put("EncryptedData",data);
        return db.insert("Notes",null,values);
    }

    //endregion

    //endregion

    //region DateReminders
    //CRUD
    public long addReminder(long timestamp){
        ContentValues values=new ContentValues();
        values.put("Alarm",timestamp);
        return db.insert("Reminder",null,values);
    }
    public long getReminder(long id){
        Cursor cursor = db.query("Reminders",new String[]{"Alarm"},"id=?",new String[]{String.valueOf(id)},null,null,null);
        long out=cursor.getLong(0);
        cursor.close();
        return out;
    }
    //no need to create update method
    public void deleteReminder(long id){
        db.delete("Reminder","id=?",new String[]{String.valueOf(id)});
    }

    public List<Tuple2<Long,Long>> getAllReminders(){
        Cursor cursor=db.query("Reminder",new String[]{"ID,Alarm"},null,null,null,null,null);
        List<Tuple2<Long,Long>> out = new LinkedList<>();
        while (cursor.moveToNext()){
            out.add(new Tuple2<>(cursor.getLong(0),cursor.getLong(1)));
        }
        cursor.close();
        return out;
    }

    //endregion

    public void closeDB(){
        db.close();
    }

    public void nukeDB(){
        db.close();
        context.deleteDatabase(db.getPath());
    }
}
