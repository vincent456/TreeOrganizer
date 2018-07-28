package vincenthudry.organizer.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class TodoDatabase extends  Database {

    public TodoDatabase(Context context) {
        super(context);
    }

    private void createToDo(long nodeID){
        ContentValues values=new ContentValues();
        values.put("ID_Node",nodeID);
        values.put("DATA","");
        db.insert("TODO_list",null,values);
    }

    private boolean isToDoInitialized(long nodeID){
        Cursor cursor=db.query("TODO_list",new String[]{"ID"},"ID_Node=?",new String[]{String.valueOf(nodeID)},null,null,null);
        boolean out = cursor.getCount()==1;
        cursor.close();
        return out;
    }

    private String getData(long nodeID){
        Cursor cursor = db.query("TODO_list",new String[]{"DATA"},"ID_Node=?",new String[]{String.valueOf(nodeID)},null,null,null);
        cursor.moveToFirst();
        String out = cursor.getString(0);
        cursor.close();
        return out;
    }

    public String getToDo(long nodeID){
        if(!isToDoInitialized(nodeID)){
            createToDo(nodeID);
        }
        return getData(nodeID);
    }

    public void setTodo(long nodeID,String data){
        if(!isToDoInitialized((nodeID))){
            createToDo(nodeID);
        }
        ContentValues values=new ContentValues();
        values.put("DATA",data);
        db.update("TODO_List",values,"ID_Node=?",new String[]{String.valueOf(nodeID)});
    }

}
