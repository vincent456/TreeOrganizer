package vincenthudry.organizer.controller.TodoDataObjectModel;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.buildware.widget.indeterm.IndeterminateCheckBox;

public class TodoLayoutItem {
    public LinearLayout root;
    public IndeterminateCheckBox checked;
    public EditText text;
    public LinearLayout children;

    public TodoLayoutItem(Context context){
        root=new LinearLayout(context);
        root.setOrientation(LinearLayout.HORIZONTAL);

        checked=new IndeterminateCheckBox(context);
        checked.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        text=new EditText(context);
        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        children=new LinearLayout(context);
        children.setOrientation(LinearLayout.VERTICAL);
        LinearLayout item = new LinearLayout(context);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        root.addView(checked);
        root.addView(item);
        item.addView(text);
        item.addView(children);
    }
}
