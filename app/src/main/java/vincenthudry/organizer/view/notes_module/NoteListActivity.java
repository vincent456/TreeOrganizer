package vincenthudry.organizer.view.notes_module;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import vincenthudry.organizer.R;
import vincenthudry.organizer.model.NotesDatabase;
import vincenthudry.organizer.utils.Tuple2;

public class NoteListActivity extends AppCompatActivity {

    long nodeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();

        final long nodeID=intent.getLongExtra("nodeID",-1);

        if(nodeID==-1){
            throw new IllegalStateException();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==NoteFragment.NEW_NOTE_REQUEST){
            if(resultCode==RESULT_OK){
                NoteFragment fragment = (NoteFragment) getSupportFragmentManager().findFragmentById(R.id.note_list_fragment);
                fragment.updateRW();
            }
        }
    }
}
