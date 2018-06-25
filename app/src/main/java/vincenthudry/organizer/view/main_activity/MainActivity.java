package vincenthudry.organizer.view.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import vincenthudry.organizer.Settings;
import vincenthudry.organizer.TreeFragment;
import vincenthudry.organizer.model.Database;
import vincenthudry.organizer.R;
import vincenthudry.organizer.utils.Tuple2;
import vincenthudry.organizer.view.reminders_module.ReminderFragment;
import vincenthudry.organizer.view.notes_module.TextListAdapter;
import vincenthudry.organizer.view.notes_module.NoteFragment;
import vincenthudry.organizer.view.notes_module.NoteTakingActivity;

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
        tabAdapter.init(new Fragment[]{new NoteFragment(), new ReminderFragment(),new TreeFragment()});
        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        db = new Database(this, Settings.databaseName);

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

        return super.onOptionsItemSelected(item);
    }

    public static final int NEW_NOTE_REQUEST = 1;

    public void FABClick(View view) {
        Intent intent = new Intent(this, NoteTakingActivity.class);
        intent.putExtra("noteID", -1);
        startActivityForResult(intent, NEW_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_NOTE_REQUEST || requestCode == TextListAdapter.UPDATE_RECYCLER)
            {
                NoteFragment notesFragment= (NoteFragment) tabAdapter.getFragments()[0];
                RecyclerView rw = notesFragment.getView().findViewById(R.id.notes_list);
                List<Tuple2<Long, String>> texts = db.getAllTitles();
                TextListAdapter adapter = new TextListAdapter(texts, this);
                rw.setAdapter(adapter);
                rw.setLayoutManager(new LinearLayoutManager(this));
                rw.invalidate();
            }
        }
    }

    @Override
    protected void onDestroy(){
        db.closeDB();
        super.onDestroy();
    }
}
