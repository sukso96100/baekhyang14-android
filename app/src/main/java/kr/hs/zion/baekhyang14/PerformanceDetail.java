package kr.hs.zion.baekhyang14;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class PerformanceDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView Title = (TextView)findViewById(R.id.title);
        TextView Time = (TextView)findViewById(R.id.time);
        TextView Performer = (TextView)findViewById(R.id.performer);
        TextView Desc = (TextView)findViewById(R.id.desc);

        Title.setText(getIntent().getStringExtra("title"));
        Time.setText(getIntent().getStringExtra("time"));
        Performer.setText(getIntent().getStringExtra("performers"));
        Desc.setText(getIntent().getStringExtra("desc"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.performance_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
