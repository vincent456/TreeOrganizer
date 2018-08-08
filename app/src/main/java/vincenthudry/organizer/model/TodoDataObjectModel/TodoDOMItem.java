package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class TodoDOMItem implements TodoDOMItemParentableInterface{
    private List<TodoDOMItem> children;
    private TodoDOMItemParentableInterface parent;
    private TodoLayoutItem viewItem;

    public TodoDOMItem(Context context){
        viewItem = new TodoLayoutItem(context);
        children=new LinkedList<>();
        parent = null;
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

    }
}
