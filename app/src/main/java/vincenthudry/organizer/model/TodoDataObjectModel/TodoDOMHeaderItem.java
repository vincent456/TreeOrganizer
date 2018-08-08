package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TodoDOMHeaderItem implements TodoDOMItemParentableInterface{
    private List<TodoDOMItem> children;
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
            viewItem.getChildren().addView(childView);
        }
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
}