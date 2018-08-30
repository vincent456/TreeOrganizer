package vincenthudry.organizer.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.LinkedList;
import java.util.List;

import vincenthudry.organizer.utils.Tuple2;

public class NotesDatabase extends Database{

    public NotesDatabase(Context context){
        super(context);
    }

    //region CRUD

    public long addNote(String title,String note,long nodeID){
        ContentValues values=new ContentValues();
        values.put("Title",title);
        values.put("Note",note);
        values.put("Encrypted",false);
        values.put("EncryptedData",new byte[]{});
        values.put("ID_Node",nodeID);
        return db.insert("Notes",null,values);
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

    public long getNodeID(long id){
        Cursor cursor=db.query("Notes",new String[]{"ID_Node"},"id=?",new String[]{String.valueOf(id)},null,null,null);
        cursor.moveToFirst();
        long out = cursor.getLong(0);
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

    public List<Tuple2<Long,String>> getAllTitles(long nodeID){
        Cursor cursor=db.query("Notes",new String[]{"ID","Title"},"ID_Node=?",new String[]{String.valueOf(nodeID)},null,null,"Title");
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


    public long addEncryptedNote(String title,String note,byte[] data,long nodeID) {
        ContentValues values=new ContentValues();
        values.put("Title",title);
        values.put("Note",note);
        values.put("Encrypted",true);
        values.put("EncryptedData",data);
        values.put("ID_Node",nodeID);
        return db.insert("Notes",null,values);
    }

    //endregion


}
