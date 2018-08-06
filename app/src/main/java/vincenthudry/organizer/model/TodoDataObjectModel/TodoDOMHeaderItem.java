package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class TodoDOMHeaderItem {
    public List<TodoDOMItem> children;
    public TodoDOMHeaderLayoutItem viewItem;

    public TodoDOMHeaderItem(Context context){
        children=new ArrayList<>();
        viewItem=new TodoDOMHeaderLayoutItem(context);
    }
}
