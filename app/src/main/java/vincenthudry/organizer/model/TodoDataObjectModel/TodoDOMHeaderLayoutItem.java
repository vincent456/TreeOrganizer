package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;
import android.widget.LinearLayout;

public class TodoDOMHeaderLayoutItem {
    private LinearLayout children;

    public LinearLayout getChildren() {
        return children;
    }

    public TodoDOMHeaderLayoutItem(Context context){
        children=new LinearLayout(context);
        children.setOrientation(LinearLayout.VERTICAL);
    }
}
