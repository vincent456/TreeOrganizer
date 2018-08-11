package vincenthudry.organizer.model.TodoDataObjectModel;

import android.content.Context;
import android.widget.EditText;

public class ParentableEditText extends EditText{
    public TodoDOMItem item;

    public ParentableEditText(Context context,TodoDOMItem item) {
        super(context);
        this.item=item;
    }
}
