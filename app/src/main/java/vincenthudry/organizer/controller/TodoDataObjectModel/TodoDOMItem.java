package vincenthudry.organizer.controller.TodoDataObjectModel;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

public class TodoDOMItem {
    public boolean checked;
    public String text;
    public List<TodoDOMItem> children;
    public TodoDOMItem parent;
    public TodoLayoutItem viewItem;

    public TodoDOMItem(Context context){
        viewItem = new TodoLayoutItem(context);
        children=new LinkedList<>();
        parent = null;
    }
}
