package vincenthudry.organizer.view.main_activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

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
        tabAdapter.init(new Fragment[]{new NoteFragment(), new ReminderFragment(),TreeFragment.newInstance()});
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.delete_node){
            WebView wv=findViewById(R.id.tree_layout);
            wv.loadUrl("javascript:followJava2()");
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
