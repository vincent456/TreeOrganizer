package vincenthudry.organizer.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.LinkedList;
import java.util.List;

public class NodesDatabase extends Database {
    public NodesDatabase(Context context) {
        super(context);
    }

    //region CRUD
    public long addNode(String title){
        ContentValues values=new ContentValues();
        values.put("title",title);
        values.putNull("id_parent");
        return  db.insert("Node",null,values);
    }

    public String getNodeTitle(long id){
        Cursor cursor=db.query("Node",new String[]{"title"},"id=?",new String[]{String.valueOf(id)},null,null,null);
        cursor.moveToFirst();
        String out = cursor.getString(0);
        cursor.close();
        return out;
    }

    public Long getNodeParent(long id){
        //TODO : return 0 instead of null
        Cursor cursor=db.query("Node",new String[]{"ID_Parent"},"id=?",new String[]{String.valueOf(id)},null,null,null);
        cursor.moveToFirst();
        Long out = cursor.getLong(0)==0?null:cursor.getLong(0);
        cursor.close();
        return out;
    }

    public List<Long> getNodeByTitle(String title){
        Cursor cursor=db.query("node",new String[]{"ID"},"Title=?",new String[]{title},null,null,null);
        List<Long> out=new LinkedList<>();
        while (cursor.moveToNext()){
            out.add(cursor.getLong(0));
        }
        cursor.close();
        return out;
    }

    public void deleteNode(long id){
        db.delete("node","id=?",new String[]{String.valueOf(id)});
    }

    //endregion

    //region hierarchy
    public void setNodeParent(long idParent, long idChild){
        ContentValues values=new ContentValues();
        values.put("ID_Parent",idParent);
        db.update("Node",values,"id=?",new String[]{String.valueOf(idChild)});
    }
    public List<Long> getNodeChildren(long id){
        List<Long> out = new LinkedList<>();
        Cursor cursor=db.query("Node",new String[]{"ID"},"ID_Parent=?",new String[]{String.valueOf(id)},null,null,null);
        while (cursor.moveToNext()){
            out.add(cursor.getLong(0));
        }
        cursor.close();
        return out;
    }

    public long addChild(long idParent,String title){
        long node=addNode(title);
        setNodeParent(idParent,node);
        return node;
    }

    //endregion

}
