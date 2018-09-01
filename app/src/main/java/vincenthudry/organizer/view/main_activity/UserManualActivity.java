package vincenthudry.organizer.view.main_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import vincenthudry.organizer.R;

public class UserManualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manual);

        Toolbar toolbar= findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView wv = findViewById(R.id.user_manual_webwiew);
        wv.loadUrl("file:android_asset/user manual.html");
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
