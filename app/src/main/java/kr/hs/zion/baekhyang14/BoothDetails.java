package kr.hs.zion.baekhyang14;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class BoothDetails extends ActionBarActivity {
    private String title;
    private String location;
    private String member;
    private String desc;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booth_details);


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setBackgroundResource(R.drawable.polygon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView Location = (TextView)findViewById(R.id.location);
        TextView Member = (TextView)findViewById(R.id.member);
        TextView Desc = (TextView)findViewById(R.id.desc);
        Button SendFeedback = (Button) findViewById(R.id.button);

        title = getIntent().getStringExtra("title");
        location = getIntent().getStringExtra("location");
        member = getIntent().getStringExtra("members");
        desc = getIntent().getStringExtra("desc");
        email = getIntent().getStringExtra("email");

        getSupportActionBar().setTitle(title);
        Location.setText(location);
        Member.setText(member);
        Desc.setText(desc);





        SendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("mailto:" + email));
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_share) {
            String StringForSharing =
                    getString(R.string.fest_name) + " - " + getString(R.string.title_activity_booth_details)
                            + "\n" + title + " - " + member + "\n" + location + "\n" + desc;

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, StringForSharing);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_booth_info)));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
