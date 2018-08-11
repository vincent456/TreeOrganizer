package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;
import android.widget.EditText;

public class ParentableEditText extends EditText{
    public TodoDOMItem parent;

    public ParentableEditText(Context context,TodoDOMItem parent) {
        super(context);
        this.parent = parent;
    }
}
