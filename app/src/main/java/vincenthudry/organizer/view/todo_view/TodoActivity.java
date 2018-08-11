package vincenthudry.organizer.view.todo_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.json.JSONObject;

import vincenthudry.organizer.R;
import vincenthudry.organizer.model.TodoDataObjectModel.ParentableEditText;
import vincenthudry.organizer.model.TodoDataObjectModel.TodoDOMHeaderItem;
import vincenthudry.organizer.model.TodoDataObjectModel.TodoDOMItem;
import vincenthudry.organizer.model.TodoDataObjectModel.TodoDOMItemParentableInterface;
import vincenthudry.organizer.model.TodoDataObjectModel.TodoLayoutItem;
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
            TodoDOMItem item  =((ParentableEditText)getCurrentFocus()).item;
            TodoDOMItemParentableInterface parent = item.getParent();
            
        }
        else {
            TodoDOMItem item = new TodoDOMItem(this);
            header.addChild(item);
            header.setupViewItem();
        }
    }

    public void remove_todo_click(View view) {
    }

    public void decrease_todo_indent_click(View view) {
    }

    public void increase_todo_indent_click(View view) {
    }

    public void hide_keyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    @Override
    public void onStop(){
        TodoDatabase tddb = new TodoDatabase(this);
        JSONObject headerJSON = header.generateJSON();
        tddb.setTodo(nodeID,headerJSON.toString());
        super.onStop();
    }
}
