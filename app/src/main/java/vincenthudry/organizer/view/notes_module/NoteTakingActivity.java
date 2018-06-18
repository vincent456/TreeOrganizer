package vincenthudry.organizer.view.notes_module;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vincenthudry.organizer.controller.NotesModule;
import vincenthudry.organizer.model.Database;
import vincenthudry.organizer.R;
import vincenthudry.organizer.Settings;

public class NoteTakingActivity extends AppCompatActivity {

    private TextInputEditText textNote;
    private EditText textTitle;
    private boolean dirty = false;

    private Database db;
    private long noteID;


    private String actionBarTilte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //region setup view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //endregion

        db = new Database(this, Settings.databaseName);
        noteID = getIntent().getExtras().getLong("noteID");

        //region set titlebar
        String s1;
        if (noteID == -1)
            s1 = getString(R.string.new_note);
        else
            s1 = "(" + noteID + ")";
        textTitle = (EditText) findViewById(R.id.editTextNoteTitle);
        textNote = (TextInputEditText) findViewById(R.id.textInputNote);

        getSupportActionBar().setTitle(s1 + getSupportActionBar().getTitle());
        actionBarTilte = getSupportActionBar().getTitle().toString();
        if (noteID != -1) {
            textTitle.setText(db.getNoteTitle(noteID));
            textNote.setText(db.getNoteContent(noteID));
            dirty = false;
        }
        //endregion

        //region add triggers for changes in text or title
        textNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dirty = true;
                getSupportActionBar().setTitle(actionBarTilte + "*");
            }
        });
        textTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dirty = true;
                getSupportActionBar().setTitle(actionBarTilte + "*");
            }
        });
        //endregion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (noteID == -1)//new note
            getMenuInflater().inflate(R.menu.menu_blank, menu);
        else
            getMenuInflater().inflate(R.menu.menu_note_taking, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.confirm_delete_note);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.deleteNote(noteID);
                    setResult(RESULT_OK);
                    finish();
                }
            });
            builder.setNegativeButton(R.string.no_cancel, null);
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveClick(View view) {
        String noteString = textNote.getText().toString();
        String noteTitle = textTitle.getText().toString();

        if (noteTitle.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.please_specify_note_title);
            builder.setPositiveButton(R.string.ok, null);
            builder.create().show();
        } else {
            if (noteID == -1 && dirty)//new note
                db.addNote(noteTitle, noteString);
            else if (dirty) {
                db.updateNote(noteID, noteTitle, noteString);
            }
            setResult(RESULT_OK);
            finish();
        }
    }

    public void cancelClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onEncryptButtonClick(View view) {
        boolean isEncrypted;
        if (noteID != -1)
            isEncrypted = db.getEncrypted(noteID);
        else {//noteId==-1
            isEncrypted = false;
        }
        final Button sender = (Button) view;
        String buttonText = sender.getText().toString();
        if (isEncrypted) {
            //region is encrypted = decryption
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.prompt_enter_password);
            final View dialView=getLayoutInflater().inflate(R.layout.dialog_password_field, null);
            builder.setView(dialView);
            final Context context = this;
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    NotesModule notesModule = new NotesModule(db);
                    TextView tv = dialView.findViewById(R.id.single_password_field);
                    String password = tv.getText().toString();
                    try {
                        notesModule.decrypt(noteID, password);
                        TextView textContent = findViewById(R.id.textInputNote);
                        textContent.setText(db.getNoteContent(noteID));
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.error_during_decryption, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();
            //endregion
        } else if (!isEncrypted
                && noteID != -1) {
            //region is not encrypted, noteid!=-1 = encryption
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.enter_password);
            final View dialView = getLayoutInflater().inflate(R.layout.dialog_password_field, null);
            builder.setView(dialView);
            final Context context = this;
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    NotesModule notesModule = new NotesModule(db);
                    TextView tv = dialView.findViewById(R.id.single_password_field);
                    String password = tv.getText().toString();
                    try {
                        if(!password.equals("")) {
                            TextView noteContent = findViewById(R.id.textInputNote);
                            String noteContentString = noteContent.getText().toString();
                            notesModule.encrypt(noteID,noteContentString, password);
                            noteContent.setText(db.getNoteContent(noteID));
                        }
                    } catch (NotesModule.DoubleEncrypt doubleEncrypt) {
                        throw new IllegalStateException();
                    }
                }
            });
            builder.show();
            //endregion
        } else if (!isEncrypted
                && noteID == -1) {
            //region is not encrypted, noteId==-1 = save as encrypted

            //region check for title
            String noteTitle = textTitle.getText().toString();

            if (noteTitle.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.please_specify_note_title);
                builder.setPositiveButton(R.string.ok, null);
                builder.create().show();
            }
            //endregion

            else {//title is correct
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.please_enter_password_for_encrypted_note);
                final View dialView=getLayoutInflater().inflate(R.layout.dialog_password_field, null);
                builder.setView(dialView);
                Context context = this;
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView tv = dialView.findViewById(R.id.single_password_field);
                        String password = tv.getText().toString();
                        saveAsEncrypted(password);
                    }
                });
                builder.show();
            }
            //endregion
        } else {
            throw new IllegalStateException();
        }
    }

    public void saveAsEncrypted(String password) {
        String noteString = textNote.getText().toString();
        String noteTitle = textTitle.getText().toString();
        if (noteID == -1 && dirty) {//new note
            NotesModule notesModule = new NotesModule(db);
            notesModule.addNewEncryptedNote(noteTitle,noteString,password);
        } else
            throw new IllegalStateException();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy(){
        db.closeDB();
        super.onDestroy();
    }
}
