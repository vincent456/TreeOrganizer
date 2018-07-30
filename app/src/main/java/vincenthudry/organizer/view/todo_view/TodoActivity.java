package vincenthudry.organizer.view.todo_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.json.JSONObject;

import vincenthudry.organizer.R;
import vincenthudry.organizer.controller.TodoGenerator;
import vincenthudry.organizer.model.TodoDatabase;

public class TodoActivity extends AppCompatActivity {

    private JSONObject data;
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
        //data = TodoGenerator.fromString(todoDataString);
        data = TodoGenerator.createHeaderItem();
        JSONObject item1=TodoGenerator.createToDoItem("test");
        TodoGenerator.addSubTask(data,item1);
        //endregion
        //region init view
        LinearLayout view = TodoGenerator.generateViewHeader(this);
        TodoGenerator.generateSubView(view,this,data);
        FrameLayout fl = findViewById(R.id.todo_frame);
        view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
        fl.addView(view);
        //endregion
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
