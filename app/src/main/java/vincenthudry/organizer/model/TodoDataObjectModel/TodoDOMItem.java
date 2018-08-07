package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

public class TodoDOMItem {
    private boolean checked;
    private String text;
    private List<TodoDOMItem> children;
    private TodoDOMItem parent;
    private TodoLayoutItem viewItem;

    public TodoLayoutItem getViewItem() {
        return viewItem;
    }

    public void setParent(TodoDOMItem parent) {
        this.parent = parent;
    }

    public TodoDOMItem(Context context){
        viewItem = new TodoLayoutItem(context);
        children=new LinkedList<>();
        parent = null;
    }

    public void addChild(TodoDOMItem child){
        children.add(child);
        child.setParent(this);
    }

}
