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


public class PerformanceDetail extends ActionBarActivity {
    private String title;
    private String turn;
    private String performer;
    private String desc;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setBackgroundResource(R.drawable.polygon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView Turn = (TextView)findViewById(R.id.turn);
        TextView Performer = (TextView)findViewById(R.id.performer);
        TextView Desc = (TextView)findViewById(R.id.desc);
        Button SendFeedback = (Button) findViewById(R.id.button);

        title = getIntent().getStringExtra("title");
        turn = getIntent().getStringExtra("turn");
        performer = getIntent().getStringExtra("performers");
        desc = getIntent().getStringExtra("desc");
        email = getIntent().getStringExtra("email");

        getSupportActionBar().setTitle(title);
        Turn.setText(turn);
        Performer.setText(performer);
        Desc.setText(desc);





        SendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FeedbackAbout = getResources().getString(R.string.feedback_about);
                String FeedbackTurn = getResources().getString(R.string.feedback_turn);
                String FeedbackDetail = getResources().getString(R.string.feedback_detail);
                String FeedbackContent = getResources().getString(R.string.feedback_body);

                Intent i = new Intent(Intent.ACTION_VIEW);
                String MailTo = "mailto:"+email+"?subject="+FeedbackAbout+title+
                        "&body="+FeedbackAbout+title+"\n"+FeedbackTurn+turn+"\n"+
                        FeedbackDetail+desc+"\n"+FeedbackContent;
                i.setData(Uri.parse(MailTo));
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
                    getString(R.string.fest_name) + " - " + getString(R.string.title_activity_performance_detail)
                    + "\n(" + turn +")" + title + " - " + performer + "\n" + desc;

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, StringForSharing);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_performance_info)));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
