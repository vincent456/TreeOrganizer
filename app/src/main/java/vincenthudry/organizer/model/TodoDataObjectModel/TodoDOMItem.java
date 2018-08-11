package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class TodoDOMItem implements TodoDOMItemParentableInterface{
    private List<TodoDOMItem> children;
    private TodoDOMItemParentableInterface parent;
    private TodoLayoutItem viewItem;

    public TodoDOMItem(Context context){
        viewItem = new TodoLayoutItem(context,this);
        children=new LinkedList<>();
        parent = null;
    }

    public TodoDOMItem(Context context, JSONObject child) {
        viewItem = new TodoLayoutItem(context,this);
        children=new LinkedList<>();
        parent=null;
        try {
            setChecked(child.getBoolean("checked"));
        } catch (JSONException e) {
           throw new RuntimeException(e);
        }
        try {
            setText(child.getString("text"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONArray children;
        try {
            children = child.getJSONArray("children");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        int cl=children.length();
        for(int i=0;i<cl;i++){
            JSONObject child_l;
            try {
                child_l = children.getJSONObject(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            addChild(new TodoDOMItem(context,child_l));
        }
    }

    public TodoLayoutItem getViewItem() {
        return viewItem;
    }

    public void setParent(TodoDOMItemParentableInterface parent) {
        this.parent = parent;
    }

    public boolean getChecked(){
        return getViewItem().getChecked().isChecked();
    }

    public void setChecked(boolean checked){
        getViewItem().setChecked(checked);
    }

    public String getText() {
        return getViewItem().getText().getText().toString();
    }

    public void setText(String text){
        getViewItem().setText(text);
    }

    public void addChild(TodoDOMItem child){
        children.add(child);
        child.setParent(this);
    }

    public JSONObject generateJSON() {
        JSONObject out = new JSONObject();
        try {
            out.accumulate("checked",getChecked());
            out.accumulate("text",getText());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JSONArray children=new JSONArray();
        try {
            out.accumulate("children",children);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for(TodoDOMItem child:this.children){
            children.put(child.generateJSON());
        }
        return out;
    }

    public void setupViewItem() {
        viewItem.getChildren().removeAllViews();
        for(TodoDOMItem child:children){
            child.setupViewItem();
            viewItem.getChildren().addView(child.getViewItem().getRoot());
        }
    }

    public TodoDOMItemParentableInterface getParent() {
        return parent;
    }
}
