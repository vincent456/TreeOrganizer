package vincenthudry.organizer.View;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import vincenthudry.organizer.Model.Database;
import vincenthudry.organizer.R;
import vincenthudry.organizer.Settings;

public class NoteTakingActivity extends AppCompatActivity {

    private  TextInputEditText textNote;
    private EditText textTitle;
    private boolean dirty=false;

    private Database db;
    private int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textNote = (TextInputEditText) findViewById(R.id.textInputNote);
        textNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dirty=true;
            }
        });
        textTitle=(EditText) findViewById(R.id.editTextNoteTitle);
        textTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dirty=true;
            }
        });
        db=new Database(this, Settings.databaseName);
        noteID=getIntent().getExtras().getInt("noteID");
        getSupportActionBar().setTitle("("+noteID+")"+getSupportActionBar().getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(noteID==-1)//new note
        getMenuInflater().inflate(R.menu.menu_blank,menu);
        else
            getMenuInflater().inflate(R.menu.menu_note_taking,menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Are you sure to delete this note ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.deleteNote(noteID);
                    setResult(RESULT_OK);
                    finish();
                }
            });
            builder.setNegativeButton("No/Cancel",null);
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveClick(View view) {
        String noteString = textNote.getText().toString();
        String noteTitle = textTitle.getText().toString();

        if(noteTitle.equals("")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("You must specify a title to your note");
            builder.setPositiveButton("Ok",null);
            builder.create().show();
        }
        else {
            if(noteID==-1&&dirty)//new note
            db.addNote(noteTitle,noteString);
            else if(dirty){
                db.updateNote(noteID,noteTitle,noteString);
            }
            setResult(RESULT_OK);
            finish();
        }
    }

    public void cancelClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
