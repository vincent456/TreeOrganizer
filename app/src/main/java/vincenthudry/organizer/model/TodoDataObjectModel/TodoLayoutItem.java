package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.buildware.widget.indeterm.IndeterminateCheckBox;

public class TodoLayoutItem {

    private LinearLayout root;
    private IndeterminateCheckBox checked;
    private ParentableEditText text;
    private LinearLayout children;

    public LinearLayout getRoot() {
        return root;
    }

    public IndeterminateCheckBox getChecked() {
        return checked;
    }

    public EditText getText() {
        return text;
    }

    public LinearLayout getChildren() {
        return children;
    }

    public TodoLayoutItem(Context context,TodoDOMItem item){
        root=new LinearLayout(context);
        root.setOrientation(LinearLayout.HORIZONTAL);

        checked=new IndeterminateCheckBox(context);
        checked.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        text=new ParentableEditText(context,item);
        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        children=new LinearLayout(context);
        children.setOrientation(LinearLayout.VERTICAL);
        LinearLayout layoutitem = new LinearLayout(context);
        layoutitem.setOrientation(LinearLayout.VERTICAL);
        layoutitem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        root.addView(checked);
        root.addView(layoutitem);
        layoutitem.addView(text);
        layoutitem.addView(children);
    }

    public void setChecked(boolean checked) {
        this.checked.setChecked(checked);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setParent(TodoDOMItem parent){
        text.parent =parent;
    }
    public TodoDOMItem getParent(){
        return text.parent;
    }
}
