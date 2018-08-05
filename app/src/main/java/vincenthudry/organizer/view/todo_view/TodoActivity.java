package vincenthudry.organizer.view.todo_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import vincenthudry.organizer.R;
import vincenthudry.organizer.model.TodoDataObjectModel.TodoLayoutItem;
import vincenthudry.organizer.model.TodoDatabase;

public class TodoActivity extends AppCompatActivity {


    private long nodeID;

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

        //region test
        TodoLayoutItem tdli=new TodoLayoutItem(this);
        FrameLayout fl = findViewById(R.id.todo_frame);
        fl.addView(tdli.root);
        //endregion
        //endregion
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void add_todo_click(View view) {
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
}
