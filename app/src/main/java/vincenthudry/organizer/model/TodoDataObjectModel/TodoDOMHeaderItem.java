package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TodoDOMHeaderItem {
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
    }

    public void setupViewItem(){
        viewItem.getChildren().removeAllViews();;
        for(TodoDOMItem item:children) {

            View childView = item.getViewItem().getRoot();
            viewItem.getChildren().addView(childView);
        }
    }
}
