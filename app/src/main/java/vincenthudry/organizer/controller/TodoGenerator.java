package vincenthudry.organizer.controller;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.buildware.widget.indeterm.IndeterminateCheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            return new JSONObject(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region View
    public static View generateView(JSONObject object, Context context){
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        LinearLayout item = new LinearLayout(context);
        item.setOrientation(LinearLayout.HORIZONTAL);

        IndeterminateCheckBox checked = new IndeterminateCheckBox(context);
        checked.setChecked(getChecked(object));
        checked.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        EditText text = new EditText(context);
        text.setText(getText(object));
        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        item.addView(checked);
        item.addView(text);

        return root;
    }
    //endregion
}
