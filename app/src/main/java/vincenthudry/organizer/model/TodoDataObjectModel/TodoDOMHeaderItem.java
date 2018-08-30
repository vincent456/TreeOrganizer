package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodoDOMHeaderItem extends TodoItemAncestor{
   // private ArrayList<TodoDOMItem> children;
    private TodoDOMHeaderLayoutItem viewItem;


    public TodoDOMHeaderLayoutItem getViewItem() {
        return viewItem;
    }

    public TodoDOMHeaderItem(Context context){
        children=new ArrayList<>();
        viewItem=new TodoDOMHeaderLayoutItem(context);
    }

    public void addChild(TodoDOMItem item){
        children.add(item);
        item.setParent(this);
    }

    public void setupViewItem(){
        viewItem.getChildren().removeAllViews();
        for(TodoDOMItem item:children) {
            View childView = item.getViewItem().getRoot();
            item.setupViewItem();
            try {
                viewItem.getChildren().addView(childView);
            }
            catch (Exception e){
                int i=1;
            }
        }
    }

    @Override
    public void removeChild(TodoDOMItem item) {
        children.remove(item);
    }

    @Override
    public int getChildIndex(TodoDOMItem item) {
        return children.indexOf(item);
    }

    @Override
    public TodoDOMItem getIthChild(int i) {
        return children.get(i);
    }

    public JSONObject generateJSON() {
        JSONObject out = new JSONObject();
        JSONArray children = new JSONArray();
        try {
            out.accumulate("children",children);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (TodoDOMItem domItem : this.children){
            JSONObject child = domItem.generateJSON();
            children.put(child);
        }
        return out;
    }

    public void fromJSON(String todoDataString,Context context) {
        if(todoDataString.equals("")){
           return;
        }
        JSONObject object= null;
        try {
            object = new JSONObject(todoDataString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONArray children = null;
        try {
            children = object.getJSONArray("children");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        int cl=children.length();
        for(int i=0;i<cl;i++){
            JSONObject child;
            try {
                child = children.getJSONObject(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            TodoDOMItem tdi = new TodoDOMItem(context,child,this);
            this.addChild(tdi);
        }
    }

    @Override
    public TodoItemAncestor getParent() {
        return null;
    }

    @Override
    public String toString(){
        return "header";
    }
}