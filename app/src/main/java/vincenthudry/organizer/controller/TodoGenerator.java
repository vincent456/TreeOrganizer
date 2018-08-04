package vincenthudry.organizer.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.buildware.widget.indeterm.IndeterminateCheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import vincenthudry.organizer.view.todo_view.NumberedEditText;

public class TodoGenerator {

    //region JSON

    public static JSONObject createToDoItem(String text){
        JSONObject out = new JSONObject();
        try {
            out.accumulate("checked", false);
            out.accumulate("text",text);
            out.accumulate("children",new JSONArray());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public static void addSubTask(JSONObject parent, JSONObject child){
        try {
            parent.getJSONArray("children").put(child);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeSubTask(JSONObject parent, int childIndex){
        try {
            parent.getJSONArray("children").remove(childIndex);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray getSubTasks(JSONObject obj){
        try {
            return obj.getJSONArray("children");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setChecked(JSONObject object, boolean status){
        try {
            object.put("checked",status);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getChecked(JSONObject object){
        try {
            return object.getBoolean("checked");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setText(JSONObject object,String text){
        try {
            object.put("text",text);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getText(JSONObject object){
        try {
            return object.getString("text");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject fromString(String s){
        try {
            if(s.equals("")){
                JSONObject out = new JSONObject();
                out.accumulate("children",new JSONArray());
                return out;
            }
            else {
                return new JSONObject(s);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    //region metadata
    public static JSONObject generateEmptyMetaData(){
        JSONObject out = new JSONObject();
        try {
            out.accumulate("parent",-1);
            out.accumulate("id",-1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public static void setMetadata(JSONObject object,JSONObject metadata){
        try {
            object.put("metadata",metadata);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getMetadata(JSONObject object){
        try {
            return object.getJSONObject("metadata");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setParent(JSONObject metadata,int parent){
        try {
            metadata.put("parent",parent);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getParent(JSONObject metadata){
        try {
            return metadata.getInt("parent");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setID(JSONObject metadata,int id){
        try {
            metadata.put("id",id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getID(JSONObject metadata){
        try {
            return metadata.getInt("id");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    //endregion

    //endregion

    //region numbers
    public static List<NumberedEditText> getAllNumberedEditText(LinearLayout ll){
        List<NumberedEditText> out = new LinkedList<>();
        getAllNumberedEditText_Rec(ll,out);
        return out;
    }

    private static void getAllNumberedEditText_Rec(ViewGroup vg,List<NumberedEditText> data){
        for(int i=0;i<vg.getChildCount();i++){
            View v = vg.getChildAt(i);
            if(v instanceof NumberedEditText){
                data.add((NumberedEditText) v);
            }
            if(v instanceof ViewGroup){
                getAllNumberedEditText_Rec((ViewGroup) v,data);
            }
        }
    }

    public static void setNumbersNumberedEditText(List<NumberedEditText> list){
        int i=1;
        for (NumberedEditText nmet :
                list) {
            nmet.number=i;
            i++;
        }
    }

    private static int number;
    public static void setNumbersData(JSONObject data){
        number=0;
        setNumbersData_Rec(data);
        number=0;
    }

    private static void setNumbersData_Rec(JSONObject data){
        try {
            data.put("id",number);
            number++;
            JSONArray children = data.getJSONArray("children");
            int c_l=children.length();
            for(int i=0;i<c_l;i++){
                JSONObject child=children.getJSONObject(i);
                setNumbersData_Rec(child);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearNumbers(JSONObject data){
        data.remove("id");
        JSONArray children;
        try {
            children = data.getJSONArray("children");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        int cl=children.length();
        for(int i=0;i<cl;i++){
            JSONObject child;
            try {
                child=children.getJSONObject(i);
                child.remove("id");
                clearNumbers(child);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public JSONObject getByNumber(int number, JSONObject object){
        if(!object.has("id")){
            return null;
        }

        int id;
        try {
            id=object.getInt("id");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if(id==number){
            return object;
        }
        else {
            JSONArray children;
            try {
                children=object.getJSONArray("children");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            int cl=children.length();
            for(int i=0;i<cl;i++){
                JSONObject child;
                try {
                    child=children.getJSONObject(i);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JSONObject res=getByNumber(number,child);
                if(res!=null){
                    return res;
                }
            }

            return null;

        }
    }

    public static void setParents(JSONObject data){
        JSONArray children;
        try {
            children = data.getJSONArray("children");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        int cl=children.length();
        int id = getID(data);
        JSONObject child;
        for(int i=0;i<cl;i++){
            try {
                child = children.getJSONObject(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            setParent(child,id);
            setParents(child);
        }
    }

    public void clearParents(JSONObject data){
        data.remove("parent");
        JSONArray children;
        try {
            children = data.getJSONArray("children");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        int cl=children.length();
        for(int i=0;i<cl;i++){
            JSONObject child;
            try {
                child=children.getJSONObject(i);
                child.remove("parent");
                clearNumbers(child);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //endregion

    //region View

    public static LinearLayout generateViewHeader(Context context){
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        return root;
    }

    private static View generateViewItem(JSONObject object, Context context){
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout item = new LinearLayout(context);
        item.setOrientation(LinearLayout.VERTICAL);

        IndeterminateCheckBox checked = new IndeterminateCheckBox(context);
        checked.setChecked(getChecked(object));
        checked.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        NumberedEditText text = new NumberedEditText(context);
        text.setText(getText(object));
        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        root.addView(checked);
        root.addView(item);
        item.addView(text);

        JSONArray children;
        try {
            children = object.getJSONArray("children");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        int cl=children.length();
        for (int i=0;i<cl;i++){
            JSONObject item_i;
            try {
                item_i = children.getJSONObject(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            item.addView(generateViewItem(item_i,context));
        }

        return root;
    }

    public static void generateSubView(LinearLayout header,Context context,JSONObject object){
        header.removeAllViews();
        JSONArray l1_items = getSubTasks(object);
        int l1_l=l1_items.length();
        for(int i=0;i<l1_l;i++){
            View item;
            try {
                item = generateViewItem(l1_items.getJSONObject(i),context);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            header.addView(item);
        }
    }
    //endregion
}
