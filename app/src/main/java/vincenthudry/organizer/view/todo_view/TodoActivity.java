package vincenthudry.organizer.view.todo_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONObject;

import vincenthudry.organizer.R;
import vincenthudry.organizer.model.TodoDataObjectModel.ParentableEditText;
import vincenthudry.organizer.model.TodoDataObjectModel.TodoDOMHeaderItem;
import vincenthudry.organizer.model.TodoDataObjectModel.TodoDOMItem;
import vincenthudry.organizer.model.TodoDataObjectModel.TodoItemAncestor;
import vincenthudry.organizer.model.TodoDatabase;

public class TodoActivity extends AppCompatActivity {


    private long nodeID;
    private TodoDOMHeaderItem header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //region init data
        nodeID = getIntent().getLongExtra("nodeID",-1);
        if(nodeID==-1){
            throw new IllegalStateException();
        }
        TodoDatabase tddb = new TodoDatabase(this);
        String todoDataString = tddb.getToDo(nodeID);

        header = new TodoDOMHeaderItem(this);
        header.fromJSON(todoDataString,this);
        header.setupViewItem();

        FrameLayout fl = findViewById(R.id.todo_frame);
        fl.addView(header.getViewItem().getChildren());

        //endregion
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void add_todo_click(View view) {
        if(getCurrentFocus() instanceof ParentableEditText){
            TodoDOMItem item  =((ParentableEditText)getCurrentFocus()).parent;
            TodoItemAncestor parent = item.getParent();
            TodoDOMItem ch=new TodoDOMItem(this,parent);
            ch.getViewItem().getText().requestFocus();
            int i = parent.getChildIndex(item);
            parent.addChild(i+1,ch);
            parent.setupViewItem();
        }
        else {
            TodoDOMItem item = new TodoDOMItem(this,header);
            item.getViewItem().getText().requestFocus();
            header.addChild(item);
            header.setupViewItem();
        }
    }

    public void remove_todo_click(View view) {
        hide_keyboard(view);
        if(!(getCurrentFocus() instanceof ParentableEditText)) {
            Toast.makeText(this, getString(R.string.todo_no_item_selected), Toast.LENGTH_SHORT).show();
            return;
        }
        ParentableEditText v = (ParentableEditText)getCurrentFocus();
        TodoDOMItem item = v.parent;
        TodoItemAncestor parent = item.getParent();
        int i =parent.getChildIndex(item);
        parent.removeChild(item);

        //region keep children

           // ArrayList<TodoDOMItem> children = item.getChildren();
           // for (TodoDOMItem tdi : children)
           //     tdi.setParent(parent);
           // item.getViewItem().getChildren().removeAllViews();
           // parent.addChild(i, children);

        //endregion

        parent.setupViewItem();
    }

    public void decrease_todo_indent_click(View view) {
        if(!(getCurrentFocus() instanceof  ParentableEditText)) {
            Toast.makeText(this, getString(R.string.todo_no_item_selected), Toast.LENGTH_SHORT).show();
            return;
        }
        TodoDOMItem current = ((ParentableEditText)getCurrentFocus()).parent;
        TodoItemAncestor parent = current.getParent();
        if(parent instanceof TodoDOMHeaderItem){
            Toast.makeText(this,getString(R.string.default_error_message),Toast.LENGTH_SHORT).show();
            return;
        }
        TodoItemAncestor grandParent = parent.getParent();
        int parentI=grandParent.getChildIndex((TodoDOMItem) parent);
        int pi1=parentI+1;
        parent.removeChild(current);
        ((TodoDOMItem) parent).getViewItem().getChildren().removeView(current.getViewItem().getRoot());
        grandParent.addChild(pi1,current);
        grandParent.setupViewItem();
    }

    public void increase_todo_indent_click(View view) {
        if(!(getCurrentFocus() instanceof  ParentableEditText)){
            Toast.makeText(this,getString(R.string.todo_no_item_selected),Toast.LENGTH_SHORT).show();
            return;
        }
        ParentableEditText v = (ParentableEditText)getCurrentFocus();
        TodoDOMItem item = v.parent;
        TodoItemAncestor parent = item.getParent();
        int i = parent.getChildIndex(item);
        int ip = i-1;
        TodoDOMItem up;
        try {
            up = parent.getIthChild(ip);
            up.addChild(item);
            parent.removeChild(item);
            parent.setupViewItem();
        }
        catch (ArrayIndexOutOfBoundsException e){
            Toast.makeText(this,getString(R.string.default_error_message),Toast.LENGTH_SHORT).show();
        }
    }

    public void hide_keyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            Toast.makeText(this, R.string.dismiss_keyboard,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop(){
        TodoDatabase tddb = new TodoDatabase(this);
        JSONObject headerJSON = header.generateJSON();
        tddb.setTodo(nodeID,headerJSON.toString());
        super.onStop();
    }

    public void up_todo(View view) {
        if(!(getCurrentFocus() instanceof ParentableEditText)){
            Toast.makeText(this,getString(R.string.todo_no_item_selected),Toast.LENGTH_SHORT).show();
            return;
        }
        TodoDOMItem current = ((ParentableEditText)getCurrentFocus()).parent;
        TodoItemAncestor parent = current.getParent();
        int i = parent.getChildIndex(current);
        int i1=i-1;
        if(i1<0) {
            //Toast.makeText(this, getString(R.string.default_error_message), Toast.LENGTH_SHORT).show();
            return;
        }
        parent.removeChild(current);
        parent.addChild(i1,current);
        parent.setupViewItem();
    }

    public void down_todo(View view) {
        if(!(getCurrentFocus() instanceof  ParentableEditText)){
            Toast.makeText(this,getString(R.string.todo_no_item_selected),Toast.LENGTH_SHORT).show();
            return;
        }
        TodoDOMItem current = ((ParentableEditText)getCurrentFocus()).parent;
        TodoItemAncestor parent = current.getParent();
        int i=parent.getChildIndex(current);
        int i1 = i+1;
        if(i1>=parent.getChildren().size()){
            return;
        }
        parent.removeChild(current);
        parent.addChild(i1,current);
        parent.setupViewItem();
    }
}
