package vincenthudry.organizer.view.main_activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import vincenthudry.organizer.model.NodesDatabase;
import vincenthudry.organizer.view.tree_layout.TreeFragment;
import vincenthudry.organizer.model.Database;
import vincenthudry.organizer.R;
import vincenthudry.organizer.view.reminders_module.ReminderFragment;
import vincenthudry.organizer.view.notes_module.NoteFragment;

public class MainActivity extends AppCompatActivity {

    Database db;

    private  ViewPager viewPager;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager=(ViewPager) findViewById(R.id.view_pager);
        tabAdapter=new TabAdapter(getSupportFragmentManager());
        tabAdapter.init(new Fragment[]{new ReminderFragment(),TreeFragment.newInstance()});
        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        db = new Database(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar parent clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.delete_node){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.are_you_sure_to_delete_this_node);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    WebView wv=findViewById(R.id.tree_layout);
                    wv.loadUrl("javascript:followJava2()");
                    wv.loadUrl("javascript:followJavaDeleteNodeContent()");
                }
            });

            builder.setNegativeButton(R.string.no_cancel,null);
            builder.create().show();
            return true;
        }

        if(id==R.id.action_rebind_parent){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.set_parent_node);
            LayoutInflater layoutInflater = getLayoutInflater();
            final View frameLayout = layoutInflater.inflate(R.layout.dialog_numeric_field,null);
            builder.setView(frameLayout);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    WebView wv=findViewById(R.id.tree_layout);
                    EditText dnf = frameLayout.findViewById(R.id.single_numeric_field_dialog);
                    int val = Integer.valueOf(dnf.getText().toString());
                    wv.loadUrl("javascript:followJava4("+String.valueOf(val)+")");
                }
            });
            builder.setNegativeButton(R.string.cancel,null);
            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        db.closeDB();
        super.onDestroy();
    }
    @Override
    protected void onStop(){
        WebView vw = findViewById(R.id.tree_layout);
        vw.loadUrl("javascript:followJava3()");
        super.onStop();
    }
}
