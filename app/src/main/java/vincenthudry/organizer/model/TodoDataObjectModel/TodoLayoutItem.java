package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.buildware.widget.indeterm.IndeterminateCheckBox;

public class TodoLayoutItem {

    private LinearLayout root;
    private IndeterminateCheckBox checked;
    private EditText text;
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

    public void setChecked(boolean checked) {
        this.checked.setChecked(checked);
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
